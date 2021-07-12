package com.github.leuvaarden.testgenericresponse;

import com.github.leuvaarden.testgenericresponse.dto.ErrorResponse;
import com.github.leuvaarden.testgenericresponse.dto.Response;
import com.github.leuvaarden.testgenericresponse.dto.SuccessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

import static java.time.OffsetDateTime.now;
import static java.util.UUID.randomUUID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/example", produces = APPLICATION_JSON_VALUE)
public class ExampleController {
    @GetMapping("/get")
    public Response<ExamplePayload> getExample(@Valid @NotNull @RequestParam String context,
                                               @Valid @NotNull @RequestParam Optional<Boolean> success) {
        if (success.isEmpty() || success.get()) {
            ExamplePayload examplePayload = new ExamplePayload(randomUUID(), context, now());
            return new SuccessResponse<>(examplePayload);
        } else {
            ErrorResponse.ErrorHolder error = new ErrorResponse.ErrorHolder("Code", "Description", "Message");
            return new ErrorResponse<>(error);
        }
    }
}
