package src.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import src.config.annotation.ApiPrefixController;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.utils.Utils;
import src.model.User;
import src.repository.IUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
//@ApiController("/users")
@ApiPrefixController(value = "/users")

public class UserController {

    @Autowired
    private IUserRepository userRepository;

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @Tag(name = "users", description = "Operations related to users")
//    @Operation(summary = "Hello API")
////   @OpenApiResponse(model = PagedResultDto.class)
//    public PagedResultDto<testDto> getUsers(@RequestParam(defaultValue = "0") int skip, @RequestParam(defaultValue = "10") int limit) {
//        List<User> users = userRepository.findAll();
//        Pagination pagination = new Pagination(
//                10, 10, 10
//        );
//        return new PagedResultDto<testDto>(pagination, users.stream().map(user -> Utils.toDto(user, testDto.class)).collect(Collectors.toList()));
//    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Tag(name = "users", description = "Operations related to users")
    @Operation(summary = "Hello API")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successful operation",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = testDto.class)))
//    })
//    public ResponseEntity<PagedResultDto<testDto>> getUsers(@RequestParam(defaultValue = "0") int skip, @RequestParam(defaultValue = "10") int limit) {
    public PagedResultDto<testDto> getUsers(@RequestParam(defaultValue = "0") int skip, @RequestParam(defaultValue = "10") int limit) {
        List<User> users = userRepository.findAll();
        Pagination pagination = new Pagination(
                10, 10, 10
        );
        PagedResultDto<testDto> a =  new PagedResultDto<testDto>(pagination, users.stream().map(user -> Utils.toDto(user, testDto.class)).collect(Collectors.toList()));
//        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(a);
        return new PagedResultDto<testDto>(pagination, users.stream().map(user -> Utils.toDto(user, testDto.class)).collect(Collectors.toList()));
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
