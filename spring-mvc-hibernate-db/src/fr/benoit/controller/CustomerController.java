package fr.benoit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.benoit.db.entity.Customer;
import fr.boris.service.CustomerService;

@Controller
@SessionAttributes("customerStore")
@RequestMapping("/")
public class CustomerController {

	@Autowired
	private CustomerService ser;

	@RequestMapping(value="/")
	public String getListCustomers(Model model) {

		return "customer";
	}

	@RequestMapping(value="/customer")
	public String showPage(Model model) {

		List<Customer> listCustomers = ser.getCustomers();

		model.addAttribute("listCustomers", listCustomers);
		return "customer-list";
	}

	@RequestMapping(value="/addCustomer")
	public String addCustomers(@ModelAttribute("cust") Customer cust, Model model) {
		Customer custom;
		System.out.println("addCustomer" + cust);

		custom = new Customer();
		model.addAttribute("cust", custom);

		return "addCust";
	}

	@RequestMapping(value="/affichage")
	public String affichage(@ModelAttribute("cust") Customer cust) {

		if(cust.getId()==0)
			ser.addCustomer(cust);
		else
			ser.updateCustomer(cust);
		return "redirect:customer";

	}

	@RequestMapping(value="/updatePage")
	public String update(@RequestParam("identity") int ident, Model model) {
		System.out.println("wqeeewq" + ident);
		model.addAttribute("cust", ser.getCustomer(ident));
		ser.updateCustomer(ser.getCustomer(ident));
		System.out.println("update" + ser.getCustomer(ident));
		return "addCust";
	}

	

	@RequestMapping(value="/removePage")
	public String removeCustomer(@RequestParam("identity") int ident, Model model) {
		Customer cust = ser.getCustomer(ident);
		ser.removeCustomer(cust);
		System.out.println(cust);
		return "redirect:customer";
		
	}
	@RequestMapping(value="/recherche")
	public String rechercheCustomer(Model model,HttpServletRequest request) {	
		String entree=request.getParameter("rechercheMot");
		System.out.println(entree);
		List<Customer> listCustomers=ser.rechercheCustomer(entree);	
		model.addAttribute("listCustomers", listCustomers);
		return "customer-list";
		
	}


	

}
