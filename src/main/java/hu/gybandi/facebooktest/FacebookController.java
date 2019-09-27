package hu.gybandi.facebooktest;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class FacebookController {

	private ConnectionRepository connectionRepository;

	public FacebookController(ConnectionRepository connectionRepository) {
		this.connectionRepository = connectionRepository;
	}

	@GetMapping
	public String helloFacebook(Model model) {
		Connection<Facebook> findPrimaryConnection = connectionRepository.findPrimaryConnection(Facebook.class);
		if (findPrimaryConnection == null) {
			return "redirect:/connect/facebook";
		}
		Facebook facebook = findPrimaryConnection.getApi();
		model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
		PagedList<Post> feed = facebook.feedOperations().getFeed();
		model.addAttribute("feed", feed);
		return "feed";
	}

	@RequestMapping(value = "feed", method = RequestMethod.GET)
	public String feed(Model model) {
		Connection<Facebook> findPrimaryConnection = connectionRepository.findPrimaryConnection(Facebook.class);

		if (findPrimaryConnection == null) {
			return "redirect:/connect/facebook";
		}

		Facebook facebook = findPrimaryConnection.getApi();
//		User userProfile = facebook.userOperations().getUserProfile(); //  (#12) bio field is deprecated for versions v2.8 and higher
		String[] fields = { "id", "email", "first_name", "last_name" };
		User userProfile = facebook.fetchObject("me", User.class, fields);
		model.addAttribute("userProfile", userProfile);
		PagedList<Post> userFeed = facebook.feedOperations().getFeed();
		model.addAttribute("userFeed", userFeed);
		return "feed";
	}
}