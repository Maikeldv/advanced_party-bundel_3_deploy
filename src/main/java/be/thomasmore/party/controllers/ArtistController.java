package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Artist;
import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping("/artistlist")
    public String artistList(Model model,@RequestParam(required = false)String keyword) {
        Iterable<Artist> artists = artistRepository.findAll();
        if (keyword!=null){
            artists = artistRepository.findArtistsByArtistNameContainingIgnoreCase(keyword);
        }
        else{
            artists = artistRepository.findAll();
        }
        model.addAttribute("artists", artists);
        model.addAttribute("keyword",keyword);
        return "artistlist";
    }

    @GetMapping({ "/artistdetails/{id}", "/artistdetails"})
    public String artistDetails(Model model,
                                @PathVariable(required = false) Integer id) {
        if (id == null) return "artistdetails";

        Optional<Artist> optionalArtist = artistRepository.findById(id);
        //noinspection OptionalIsPresent
        if (optionalArtist.isPresent()) {
            model.addAttribute("artist", optionalArtist.get());
        }
        return "artistdetails";
    }

    @GetMapping({"/artistdetails/{id}/prev"})
    public String artistdetailsPrev(Model model, @PathVariable int id) {
        Optional<Artist> prevArtistFromDb = artistRepository.findFirstByIdLessThanOrderByIdDesc(id);
        if (prevArtistFromDb.isPresent())
            return String.format("redirect:/artistdetails/%d", prevArtistFromDb.get().getId());
        Optional<Artist> lastArtistFromDb = artistRepository.findFirstByOrderByIdDesc();
        if (lastArtistFromDb.isPresent())
            return String.format("redirect:/artistdetails/%d", lastArtistFromDb.get().getId());
        return "artistdetails";
    }

    @GetMapping({"/artistdetails/{id}/next"})
    public String artistdetailsNext(Model model, @PathVariable int id) {
        Optional<Artist> nextArtistFromDb = artistRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
        if (nextArtistFromDb.isPresent())
            return String.format("redirect:/artistdetails/%d", nextArtistFromDb.get().getId());
        Optional<Artist> firstArtistFromDb = artistRepository.findFirstByOrderByIdAsc();
        if (firstArtistFromDb.isPresent())
            return String.format("redirect:/artistdetails/%d", firstArtistFromDb.get().getId());
        return "artistdetails";
    }

}
