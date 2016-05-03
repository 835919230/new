package servlet;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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

import com.jspsmart.upload.SmartUpload;

import util.DbManager;
import DAO.PChoiceDAO;
import DAO.PItemDAO;
import DAO.PublishDAO;
import entity.PChoice;
import entity.PItem;
import entity.Publish;

/**
 * Servlet implementation class DownServlet
 */
@WebServlet(urlPatterns = { "/DownData" })
public class DownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DownServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String flag = request.getParameter("pid");
		Integer pId = 0;
		String filename = request.getParameter("filename");
		try{
			if(flag!=null&&!"".equals(flag)){
				pId = Integer.parseInt(flag);
				String path = getServletContext().getRealPath("/")+"data/"+filename+".png";
				
				saveAsFile(path,pId);
				
				File file = new File(path);
				System.out.println(path);
				if(file.exists()){
					SmartUpload su = new SmartUpload();
					su.initialize(getServletConfig(),request,response);
					su.setContentDisposition(null);
					su.downloadFile(path);
				}else{
//					request.setAttribute("errorResult", "�ļ������ڻ�����ʧ��");
//					request.getRequestDispatcher("jsp/upload.jsp").forward(request, response);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	private static String saveAsFile(String outputPath,Integer pId) throws Exception{
		FileOutputStream out = null;
		String result = null;
		try{
			File outFile = new File(outputPath);
			if(!outFile.getParentFile().exists()){
				outFile.getParentFile().mkdirs();
			}
			if(outFile.exists()){
				outFile.delete();
			}
			StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
		       //���ñ�������  
		       standardChartTheme.setExtraLargeFont(new Font("����",Font.BOLD,20));  
		       //����ͼ��������  
		       standardChartTheme.setRegularFont(new Font("����",Font.PLAIN,15));  
		       //�������������  
		       standardChartTheme.setLargeFont(new Font("����",Font.PLAIN,15));  
		       //Ӧ��������ʽ  
		       ChartFactory.setChartTheme(standardChartTheme);
		       CategoryDataset dataset = createDataSet(pId);
			Publish publish = PublishDAO.findById(pId);
			Integer numbers = DbManager.getCount("select count(pcid) from pchoice where pid = "+pId);
			JFreeChart jfreechart = ChartFactory.createBarChart(publish.getpName(),//����
																"ѡ��",//�����ǩ
																"����", //�����ǩ
																dataset, 
																PlotOrientation.VERTICAL, 
																true,//legend 
																false, //tooltips
																false);//URLs
			
			
			out = new FileOutputStream(outFile);
			ChartUtilities.writeChartAsPNG(out, jfreechart, numbers*80, 500);
			out.flush();
			out.close();
			result = publish.getpName();
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	private static CategoryDataset createDataSet(Integer pId) throws Exception{
		Publish publish = PublishDAO.findById(pId);
		List<PItem> pitems = PItemDAO.listByPublish(publish);
		List<PChoice> pchoices = null;
		DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
		for(int i = 0;i < pitems.size();i++){
			if(pitems.get(i).getType().indexOf("���������")!=-1||
					pitems.get(i).getType().indexOf("���������")!=-1||
					pitems.get(i).getType().indexOf("���������")!=-1||
					pitems.get(i).getType().indexOf("�ֻ�")!=-1||
					pitems.get(i).getType().indexOf("����")!=-1)
				continue;
			pchoices = PChoiceDAO.listByPItem(pitems.get(i));
			for(int j = 0;j < pchoices.size();j++){
				defaultCategoryDataset.addValue(pchoices.get(j).getNumbers(), pchoices.get(j).getPcName(), pitems.get(i).getName());
			}
		}
		return defaultCategoryDataset;
	}
	
}
