package com.Controller;

import com.Model.Cat;
import com.Model.JdbcCatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(path = "/api", produces = "application/json")
@CrossOrigin(origins = "*")
public class CatController {

    private final JdbcCatRepository jdbcCatRepository;

    @Autowired
    public CatController(JdbcCatRepository jdbcCatRepository) {
        this.jdbcCatRepository = jdbcCatRepository;
    }

    @GetMapping("/cats")
    public Iterable<Cat> getAllCats() {
        Iterable<Cat> cats = jdbcCatRepository.findAllCats();
        return cats;
    }

    @GetMapping("/cat/{id}")
    public Cat getCatById(@PathVariable("id") int id) {
        log.info(""+id);
        Cat cat = jdbcCatRepository.findCatById(id);
        return cat;
    }

    @PostMapping(value = "/addcat", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Cat addNewCat(@RequestBody Cat cat) {
        return jdbcCatRepository.saveCat(cat);
    }

}
