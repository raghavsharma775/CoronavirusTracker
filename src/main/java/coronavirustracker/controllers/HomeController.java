package coronavirustracker.controllers;

import coronavirustracker.models.LocationStats;
import coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
//we want to be able to access URL and render these stats in HTML page.
//So, We are going to create controller for this purpose.
@Controller// we are using HTML controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    //we will create a model where we will put things then in template/home.html where we will be
   // rendering the html, we can acces things from the model and then construct html.
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        //picking list of objects from allstats, converting into a stream and mapping into integer.
        //each object maps to integer value(total cases for that record).
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return "home";// it will return it to template/home.html
    }
}
