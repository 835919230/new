package servlet;

import java.awt.Font;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import util.DbManager;
import DAO.PChoiceDAO;
import DAO.PItemDAO;
import DAO.PublishDAO;
import entity.PChoice;
import entity.PItem;
import entity.Publish;

/**
 * Servlet implementation class BarChartServlet
 */
@WebServlet("/BarChart")
public class BarChartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public BarChartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/png");
		String flag = request.getParameter("pid");
		Integer pId = 0;
		StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
	       //设置标题字体  
	       standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));  
	       //设置图例的字体  
	       standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));  
	       //设置轴向的字体  
	       standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));  
	       //应用主题样式  
	       ChartFactory.setChartTheme(standardChartTheme);
		synchronized (this) {
			try{
				if(flag!=null&&!"".equals(flag)){
					pId = Integer.parseInt(flag);
					CategoryDataset dataset = createDataSet(pId);
					Publish publish = PublishDAO.findById(pId);
					Integer numbers = DbManager.getCount("select count(pcid) from pchoice where pid = "+pId);
					JFreeChart jfreechart = ChartFactory.createBarChart(publish.getpName(),//标题
																		"选项",//横轴标签
																		"数量", //纵轴标签
																		dataset, 
																		PlotOrientation.VERTICAL, 
																		true,//legend 
																		false, //tooltips
																		false);//URLs
					
					ChartUtilities.writeChartAsPNG(response.getOutputStream(), jfreechart, numbers*80, 500);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	private static CategoryDataset createDataSet(Integer pId) throws Exception{
		Publish publish = PublishDAO.findById(pId);
		List<PItem> pitems = PItemDAO.listByPublish(publish);
		List<PChoice> pchoices = null;
		DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
		for(int i = 0;i < pitems.size();i++){
			if(pitems.get(i).getType().indexOf("单行填空题")!=-1||
					pitems.get(i).getType().indexOf("多行填空题")!=-1||
					pitems.get(i).getType().indexOf("多项填空题")!=-1||
					pitems.get(i).getType().indexOf("手机")!=-1||
					pitems.get(i).getType().indexOf("邮箱")!=-1)
				continue;
			pchoices = PChoiceDAO.listByPItem(pitems.get(i));
			for(int j = 0;j < pchoices.size();j++){
				defaultCategoryDataset.addValue(pchoices.get(j).getNumbers(), pchoices.get(j).getPcName(), pitems.get(i).getName());
			}
		}
		return defaultCategoryDataset;
	}

}
