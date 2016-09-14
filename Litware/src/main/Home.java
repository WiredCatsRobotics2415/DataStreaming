package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Home
 * Jamal Hashim
 */
@WebServlet("/home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
    static ArrayList<String> current = new ArrayList<String>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.setIntHeader("Refresh", 1);
		PrintWriter out = response.getWriter();
		
		//out.append("RAM Available (Jamal's MBA): " + Runtime.getRuntime().freeMemory()/1024/1024 + Math.random());
		//out.append(current.get(current.size()-1));
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html; charset=UTF-8");
		out.append("Last 50 values with times recieved:");
		RequestDispatcher rd = request.getRequestDispatcher("HTML.jsp");
		//rd.include(request, response);
		for(int i = current.size()-1;i>current.size()-50;i--){
			if(i>0){
			out.append(current.get(i)+"<br>");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*try {
			Thread.sleep(3000);
			int x = 3;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		current.add(String.valueOf("Time: " + request.getParameter("timeStamp")+ " Left: "+ request.getParameter("left")+ " Right: " + request.getParameter("right")));
		
		 
		
		//System.out.println(current);
		//doGet(request, response);
		
	}

}
