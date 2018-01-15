
 
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import org.babos.car.CarService;
import org.babos.car.Car;
 
@ManagedBean
public class index implements Serializable {
     
    private List<Car> cars;
         
    //@ManagedProperty("#{carService}")
    //private CarService service;
     
    @PostConstruct
    public void init() {
        cars = CarService.createCarlist();
    }
 
    public List<Car> getCars() {
        return cars;
    }
 
 //   public void setService(CarService service) {
 //       this.service = service;
 //   }
    
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        FontFactory.register("C:\\Windows\\Fonts\\ariblk.ttf", "Arial Black");
        pdf.setPageSize(PageSize.A4.rotate());
 //       pdf.open();
     }
    
    
private void PrintReportToPrinter(JasperPrint jasperPrint) throws JRException {

    //Get the printers names
    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

    //Lets set the printer name based on the registered printers driver name (you can see the printer names in the services variable at debugging) 
    String selectedPrinter = "Microsoft XPS Document Writer";   
    // String selectedPrinter = "\\\\S-BPPRINT\\HP Color LaserJet 4700"; // examlpe to network shared printer
    
    System.out.println("Number of print services: " + services.length);
    PrintService selectedService = null;

    //Set the printing settings
    PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
    printRequestAttributeSet.add(MediaSizeName.ISO_A4);
    printRequestAttributeSet.add(new Copies(1));
    if (jasperPrint.getOrientationValue() == net.sf.jasperreports.engine.type.OrientationEnum.LANDSCAPE) { 
      printRequestAttributeSet.add(OrientationRequested.LANDSCAPE); 
    } else { 
      printRequestAttributeSet.add(OrientationRequested.PORTRAIT); 
    } 
    PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
    printServiceAttributeSet.add(new PrinterName(selectedPrinter, null));

    JRPrintServiceExporter exporter = new JRPrintServiceExporter();
    SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
    configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
    configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
    configuration.setDisplayPageDialog(false);
    configuration.setDisplayPrintDialog(false);

    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
    exporter.setConfiguration(configuration);

    //Iterate through available printer, and once matched with our CANON PIXMA, go ahead and print!
    if(services.length != 0 || services != null){
      for(PrintService service : services){
          String existingPrinter = service.getName();
          if(existingPrinter.equals(selectedPrinter))
          {
              selectedService = service;
              break;
          }
      }
    }
    if(selectedService != null)
    {	
      try{
          //Lets the printer do its magic!
          exporter.exportReport();
      }catch(Exception e){
	System.out.println("JasperReport Error: "+e.getMessage());
      }
    }else{
      System.out.println("JasperReport Error: Printer not found!");
    }    
}
    
    
    
private void PrintReportToPrinter1(JasperPrint jasperPrint) throws JRException {
    // printRequestAttributeSet.add(MediaSizeName.ISO_A4); //setting page size
 //   printRequestAttributeSet.add(new Copies(1));
//    PrinterName printerName = new PrinterName("Microsoft XPS Document Writer", null); //gets printer 


    //Get the printers job and names
    PrinterJob printerJob = PrinterJob.getPrinterJob();
    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

    //Lets set the printer name based on the registered printers driver name  
    // String selectedPrinter = "Microsoft XPS Document Writer";  // 
    String selectedPrinter = "\\\\s-bpprint\\Konica Központi nyomtató";
    // String selectedPrinter = "\\\\S-BPPRINT\\HP Color LaserJet 4700";
    
    System.out.println("Number of print services: " + services.length);
    PrintService selectedService = null;

    //Set the printing settings
    PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
    printRequestAttributeSet.add(MediaSizeName.ISO_A4);
    printRequestAttributeSet.add(new Copies(1));
    if (jasperPrint.getOrientationValue() == net.sf.jasperreports.engine.type.OrientationEnum.LANDSCAPE) { 
      printRequestAttributeSet.add(OrientationRequested.LANDSCAPE); 
    } else { 
      printRequestAttributeSet.add(OrientationRequested.PORTRAIT); 
    } 
    PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
    printServiceAttributeSet.add(new PrinterName(selectedPrinter, null));

    JRPrintServiceExporter exporter = new JRPrintServiceExporter();
    SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
    configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
    configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
    configuration.setDisplayPageDialog(false);
    configuration.setDisplayPrintDialog(false);

    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
    exporter.setConfiguration(configuration);

    //Iterate through available printer, and once matched with our CANON PIXMA, go ahead and print!
    if(services.length != 0 || services != null){
      for(PrintService service : services){
          String existingPrinter = service.getName();
          if(existingPrinter.equals(selectedPrinter))
          {
              selectedService = service;
              break;
          }
      }
    }
    if(selectedService != null)
    {	
      try{
          //Lets the printer do its magic!
          exporter.exportReport();
      }catch(Exception e){
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "JasperReport Error!", e.getMessage()));
      }
    }else{
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "JasperReport Error!", "Printer not found!"));
    }    
}

 public void printAction(ActionEvent actionEvent) {

            try {

                String absoluteWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

                JasperReport jasperReport = JasperCompileManager.compileReport(absoluteWebPath+"report4.jrxml");        
                //report3.jasper: JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(absoluteWebPath+"report3.jasper");
                //JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(absoluteWebPath+"report4.jasper");
                System.out.println("JasperReport : compileReport OK: "+jasperReport.getName());

                Map parameters = new HashMap();
                parameters.put("ReportTitle", "Cars");
                
                // Fill the Jasper Report
                //report3.jasper: JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);  ez nem jó, mert üres lapot ad vissza, mert bug-os.
                //report3.jasper: JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource()); 
                
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(cars));
                System.out.println("JasperReport : fillReport OK "+jasperReport.getName());

                // print Jasper Reports
                // JasperPrintManager.printReport(jasperPrint, false);
                
                PrintReportToPrinter(jasperPrint);

                   
            }
            catch (JRException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "JasperReport Error!", e.getMessage()));
            }
      
    }
    
    
    public void pdfAction(ActionEvent actionEvent) {

            try {

                String absoluteWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

                JasperReport jasperReport = JasperCompileManager.compileReport(absoluteWebPath+"report4.jrxml");        
                //report3.jasper: JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(absoluteWebPath+"report3.jasper");
                //JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(absoluteWebPath+"report4.jasper");
                System.out.println("JasperReport : compileReport OK: "+jasperReport.getName());

                Map parameters = new HashMap();
                parameters.put("ReportTitle", "Cars");
                
                // Fill the Jasper Report
                //report3.jasper: JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);  ez nem jó, mert üres lapot ad vissza, mert bug-os.
                //report3.jasper: JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource()); 
                
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(cars));
                System.out.println("JasperReport : fillReport OK "+jasperReport.getName());

                // Creation of the HTML Jasper Reports
                JasperExportManager.exportReportToHtmlFile(jasperPrint, "d:\\Inkasszo2\\report\\MyJasperReport.html");
                
                JasperExportManager.exportReportToPdfFile(jasperPrint, "d:\\Inkasszo2\\report\\MyJasperReport.pdf");
                   
            }
            catch (JRException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "JasperReport Error!", e.getMessage()));
            }

      
    }
    
    
    public void xmldownloadAction(ActionEvent actionEvent) {
        try {

            String absoluteWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

            //JasperReport jasperReport = JasperCompileManager.compileReport(absoluteWebPath+"report4.jasper");        
            //report3.jasper: JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(absoluteWebPath+"report3.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(absoluteWebPath+"report4.jasper");
            System.out.println("JasperReport : compileReport OK: "+jasperReport.getName());

            Map parameters = new HashMap();
            parameters.put("ReportTitle", "Cars");

            // Fill the Jasper Report
            //report3.jasper: JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);  ez nem jó, mert üres lapot ad vissza, mert bug-os.
            //report3.jasper: JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource()); 

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(cars));
            System.out.println("JasperReport : fillReport OK "+jasperReport.getName());

           // download 
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  

            response.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
            response.setContentType("application/xml"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.
            response.setHeader("Content-disposition", "attachment; filename=\"MyJasperReport.xml\""); // The Save As popup magic is done here. You can give it any filename you want, this only won't work in MSIE, it will use current request URL as filename instead.

            try {
                JasperExportManager.exportReportToXmlStream(jasperPrint,  response.getOutputStream()); 
            }
            catch (IOException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "JasperReport exportReportToXmlStream!", e.getMessage()));
            }
            FacesContext.getCurrentInstance().getResponseComplete();  
        }
        catch (JRException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "JasperReport Error!", e.getMessage()));
        }
    }
    
    public void pdfdownloadAction(ActionEvent actionEvent) {
        try {
            String absoluteWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");

            //JasperReport jasperReport = JasperCompileManager.compileReport(absoluteWebPath+"report4.jasper");        
            //report3.jasper: JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(absoluteWebPath+"report3.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(absoluteWebPath+"report4.jasper");
            System.out.println("JasperReport : compileReport OK: "+jasperReport.getName());

            Map parameters = new HashMap();
            parameters.put("ReportTitle", "Cars");

            // Fill the Jasper Report
            //report3.jasper: JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);  ez nem jó, mert üres lapot ad vissza, mert bug-os.
            //report3.jasper: JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource()); 

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(cars));
            System.out.println("JasperReport : fillReport OK "+jasperReport.getName());

           // download 
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  

            response.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
            response.setContentType("application/pdf"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.
            response.setHeader("Content-disposition", "attachment; filename=\"MyJasperReport.pdf\""); // The Save As popup magic is done here. You can give it any filename you want, this only won't work in MSIE, it will use current request URL as filename instead.

            try {
                JasperExportManager.exportReportToPdfStream(jasperPrint,  response.getOutputStream()); 
            }
            catch (IOException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "JasperReport exportReportToPdfStream!", e.getMessage()));
            }
            FacesContext.getCurrentInstance().getResponseComplete();  
        }
        catch (JRException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "JasperReport Error!", e.getMessage()));
        }
    }

        
        
}
