package com.plumstep.controller;

import com.plumstep.model.PersonalInfo;
import com.plumstep.repository.PersonalInfoRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: joutwate
 * Date: 9/16/16
 * Time: 11:51 PM
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("pi")
public class PersonalInfoController {
    @Autowired
    private PersonalInfoRepository repository;

    @ApiOperation(value = "Create a new personal information entry")
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    ResponseEntity<PersonalInfo> createPI(@RequestBody PersonalInfo pi) {
        PersonalInfo result = repository.save(pi);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "Retrieve personal information identified by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<PersonalInfo> getPI(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findOne(id));
    }

    @ApiOperation(value = "Retrieve personal information identified by ssn")
    @RequestMapping(value = "/ssn/{ssn}", method = RequestMethod.GET)
    ResponseEntity<List<PersonalInfo>> getPI(@PathVariable String ssn) {
        return ResponseEntity.ok(repository.findBySsn(ssn));
    }
}
