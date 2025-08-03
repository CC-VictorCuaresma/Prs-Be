package pe.edu.vallegrande.vgmsdistribution.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.vgmsdistribution.application.services.DistributionProgramService;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.ResponseDto;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.request.DistributionProgramCreateRequest;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.response.DistributionProgramResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v2/programs")
@CrossOrigin("*")
@RequiredArgsConstructor
public class DistributionProgramRest {

    private final DistributionProgramService programService;

    @GetMapping
    public Mono<ResponseDto<List<DistributionProgramResponse>>> getAll() {
        return programService.getAll()
                .collectList()
                .map(list -> new ResponseDto<>(true, list));
    }

    @GetMapping("/{id}")
    public Mono<ResponseDto<DistributionProgramResponse>> getById(@PathVariable String id) {
        return programService.getById(id)
                .map(data -> new ResponseDto<>(true, data));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseDto<DistributionProgramResponse>> create(@RequestBody DistributionProgramCreateRequest request) {
        return programService.save(request)
                .map(data -> new ResponseDto<>(true, data));
    }

    @PutMapping("/{id}")
    public Mono<ResponseDto<DistributionProgramResponse>> update(@PathVariable String id, @RequestBody DistributionProgramCreateRequest request) {
        return programService.update(id, request)
                .map(data -> new ResponseDto<>(true, data));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseDto<Void>> delete(@PathVariable String id) {
        return programService.delete(id)
                .thenReturn(new ResponseDto<>(true, null));
    }
}
