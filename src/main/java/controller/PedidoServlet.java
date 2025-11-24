package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.ItemPedidoDAO;
import DAO.PedidoDAO;
import DAO.ProdutoDAO;
import model.ItemPedido;
import model.Pedido;
import model.Produto;
import model.Usuario;

@WebServlet("/pedido")
public class PedidoServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if(action == null) {
            action = "listar";
        }
        
        switch(action) {
            case "carrinho":
                mostrarCarrinho(request, response);
                break;
            case "detalhes":
                mostrarDetalhes(request, response);
                break;
            case "excluir":
                excluirPedido(request, response);
                break;
            case "remover_item":
                removerItemCarrinho(request, response);
                break;
            case "limpar_carrinho":
                limparCarrinho(request, response);
                break;
            default:
                listarPedidos(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if("adicionar_carrinho".equals(action)) {
            adicionarAoCarrinho(request, response);
        } else if("finalizar_pedido".equals(action)) {
            finalizarPedido(request, response);
        } else if("atualizar_status".equals(action)) {
            atualizarStatus(request, response);
        }
    }
        
     // Listar todos os pedidos
        private void listarPedidos(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            
            List<Pedido> pedidos = pedidoDAO.listarTodos();
            request.setAttribute("pedidos", pedidos);
            RequestDispatcher rd = request.getRequestDispatcher("/pedido/pedidos.jsp");
            rd.forward(request, response);
        }
        
        // Adicionar produto ao carrinho (sessão)
        @SuppressWarnings("unchecked")
        private void adicionarAoCarrinho(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        	
        	int idProduto = Integer.parseInt(request.getParameter("id_produto"));
        	int quantidade = Integer.parseInt(request.getParameter("quantidade"));
        	
        	// Buscar produto
        	Produto prod = produtoDAO.buscarPorId(idProduto);
        	
        	if(prod == null) {
        		response.sendRedirect(request.getContextPath() + "/pages/index.jsp?erro=produto_nao_encontrado");
        		return;
        	}
        	
        	// Pegando carrinho da session
        	HttpSession session = request.getSession();
        	Map<Integer, ItemPedido> carrinho = (Map<Integer, ItemPedido>) session.getAttribute("carrinho");
        	
        	if(carrinho == null) {
        		carrinho = new HashMap<>();
        	}
        	
        	// Verificando se o produto já esta no carrinho
        	if(carrinho.containsKey(idProduto)) {
        		ItemPedido itemExis = carrinho.get(idProduto);
        		itemExis.setQuantidade_item(itemExis.getQuantidade_item() + quantidade);
        	} else {
        		// Adicionar novo item
        		 ItemPedido novoItem = new ItemPedido();
                 novoItem.setId_produto(idProduto);
                 novoItem.setQuantidade_item(quantidade);
                 novoItem.setPreco_unitario(prod.getPreco_produto());
                 novoItem.setNome_produto(prod.getNome_produto());
                 
                 carrinho.put(idProduto, novoItem);
        	}
        	
        	session.setAttribute("carrinho", carrinho);
        	
        	response.sendRedirect(request.getContextPath() + "/pedido?action=carrinho");
        }
    
        // Mostrar carrinho
        @SuppressWarnings("unchecked")
        private void mostrarCarrinho(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            
            HttpSession session = request.getSession();
            Map<Integer, ItemPedido> carrinho = (Map<Integer, ItemPedido>) session.getAttribute("carrinho");
            
            if(carrinho == null) {
                carrinho = new HashMap<>();
            }
            
            request.setAttribute("carrinho", carrinho.values());
            RequestDispatcher rd = request.getRequestDispatcher("/pedido/carrinho.jsp");
            rd.forward(request, response);
        }
        
        // Finalizar pedido
        @SuppressWarnings("unchecked")
        private void finalizarPedido(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        	
        	HttpSession session = request.getSession();
        	Usuario usu = (Usuario) session.getAttribute("usuarioLogado");
        	 
            // VERIFICAÇÃO 1: Usuário está logado?
            if(usu == null) {
                System.err.println("ERRO: Usuário não está logado");
                response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
                return;
            }
            
            // VERIFICAÇÃO 2: Usuário completou cadastro de cliente?
            if(usu.getId_cliente() == null) {
                System.err.println("ERRO: Usuário não tem id_cliente. Redirecionando para completar cadastro.");
                response.sendRedirect(request.getContextPath() + "/cliente/comp_cad.jsp");
                return;
            }
        	
        	 Map<Integer, ItemPedido> carrinho = (Map<Integer, ItemPedido>) session.getAttribute("carrinho");
             
             if(carrinho == null || carrinho.isEmpty()) {
                 response.sendRedirect(request.getContextPath() + "/pedido?action=carrinho&erro=carrinho_vazio");
                 return;
             }
             
             // Calcular total
             BigDecimal total = BigDecimal.ZERO;
             for(ItemPedido item : carrinho.values()) {
            	 total = total.add(item.getSubtotal());
             }
             
             // Criar pedido
             Pedido pedido = new Pedido();
             pedido.setId_cliente(usu.getId_cliente());
             pedido.setTotal_pedido(total);
             
             int idPedido = pedidoDAO.inserir(pedido);
             
             if(idPedido > 0) {
            	 // Inserir itens do pedido
            	 for(ItemPedido item : carrinho.values()) {
            		 item.setId_pedido(idPedido);
            		 itemPedidoDAO.inserir(item);
            	 }
            	 
            	 //Limpando carrinho
            	 session.removeAttribute("carrinho");
            	 
            	 response.sendRedirect(request.getContextPath() + "/pedido?action=detalhes&id=" + idPedido + "&sucesso=true");
             }else {
                 response.sendRedirect(request.getContextPath() + "/pedido?action=carrinho&erro=erro_ao_finalizar");
             }
        }
        
        // Mostrar detalhes do pedido
        private void mostrarDetalhes(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            
            int id = Integer.parseInt(request.getParameter("id"));
            
            Pedido pedido = pedidoDAO.buscarPorId(id);
            List<ItemPedido> itens = itemPedidoDAO.listarPorPedido(id);
            
            pedido.setItens(itens);
            
            request.setAttribute("pedido", pedido);
            RequestDispatcher rd = request.getRequestDispatcher("/pedido/detalhes.jsp");
            rd.forward(request, response);
        }
        
        // Atualizar status do pedido
        private void atualizarStatus(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            
            int id = Integer.parseInt(request.getParameter("id"));
            String novoStatus = request.getParameter("status");
            
            pedidoDAO.atualizarStatus(id, novoStatus);
            
            response.sendRedirect(request.getContextPath() + "/pedido?action=listar");
        }
        
        // Excluir pedido
        private void excluirPedido(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            
            int id = Integer.parseInt(request.getParameter("id"));
            
            // Excluir itens primeiro (cascade) - Nao existira pedidos sem itens 
            itemPedidoDAO.excluirPorPedido(id);
            
            // Excluir pedido
            pedidoDAO.excluir(id);
            
            response.sendRedirect(request.getContextPath() + "/pedido?action=listar");
        }
        
     // Remover item específico do carrinho
        @SuppressWarnings("unchecked")
        private void removerItemCarrinho(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            
            int idProduto = Integer.parseInt(request.getParameter("id_produto"));
            
            HttpSession session = request.getSession();
            Map<Integer, ItemPedido> carrinho = (Map<Integer, ItemPedido>) session.getAttribute("carrinho");
            
            if(carrinho != null) {
                carrinho.remove(idProduto);
                session.setAttribute("carrinho", carrinho);
            }
            
            response.sendRedirect(request.getContextPath() + "/pedido?action=carrinho");
        }
        
        // Limpar todo o carrinho
        private void limparCarrinho(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            
            HttpSession session = request.getSession();
            session.removeAttribute("carrinho");
            
            response.sendRedirect(request.getContextPath() + "/pedido?action=carrinho");
        }
}
