const fs = require('fs');
const path = require('path');


const tempRepository = (name) => {
    return `
    package src.repository;

import src.model.${name};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface I${name}Repository extends JpaRepository<${name}, UUID> {
}

    `
}

const tempService = (name) => `
package src.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.model.${name};
import src.repository.I${name}Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ${name}Service {
    private  I${name}Repository ${name.toLowerCase()}Repository;
    @Autowired
    public ${name}Service(I${name}Repository ${name.toLowerCase()}Repository) {
        this.${name.toLowerCase()}Repository = ${name.toLowerCase()}Repository;
    }

    public List<${name}> getAll() {
        return (List<${name}>) ${name.toLowerCase()}Repository.findAll();
    }

    public Optional<${name}> getOne(UUID id) {
        return ${name.toLowerCase()}Repository.findById(id);
    }

    public ${name} create(${name} ${name.toLowerCase()}) {
        return ${name.toLowerCase()}Repository.save(${name.toLowerCase()});
    }

    public ${name} update(UUID id, ${name} ${name.toLowerCase()}) {
        ${name} existing${name} = ${name.toLowerCase()}Repository.findById(id).orElse(null);
        if (existing${name} != null) {

            ${name.toLowerCase()}Repository.save(existing${name});
        }
        return existing${name};
    }

    public void remove(UUID id) {
        ${name.toLowerCase()}Repository.deleteById(id);
    }
}
`

const tempDto = (name) => `
package src.dto;

public class ${name}Dto {
}`
const createFile = (dir, templateFileName, template) =>
    fs.readdir('./model', function (err, files) {
        if (err) {
            console.log('Error getting directory information:', err);
        } else {
            files.forEach(function (file) {
                const name = file.split('.')[0]
                const fileName = templateFileName.replace("###", name)
                fs.writeFile(`./${dir}/${fileName}`, template(name), function (err) {
                    if (err) throw err;
                    console.log(`File ${fileName} created successfully.`);
                });
            });
        }
    });


// // create file in repository
// createFile("repository", "I###Repository.java", tempRepository)
// // create file in dto
createFile("dto", "###Dto.java", tempDto)
// create file in service
createFile("service", "###Service.java", tempService)