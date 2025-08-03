package pe.edu.vallegrande.vgmsdistribution.application.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vgmsdistribution.application.services.DistributionProgramService;
import pe.edu.vallegrande.vgmsdistribution.domain.models.DistributionProgram;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.request.DistributionProgramCreateRequest;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.dto.response.DistributionProgramResponse;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.exception.CustomException;
import pe.edu.vallegrande.vgmsdistribution.infrastructure.repository.DistributionProgramRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class DistributionProgramServiceImpl implements DistributionProgramService {

    private final DistributionProgramRepository programRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Flux<DistributionProgramResponse> getAll() {
        return programRepository.findAll()
                .map(this::toResponse);
    }

    @Override
    public Mono<DistributionProgramResponse> getById(String id) {
        return programRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(
                        HttpStatus.NOT_FOUND.value(), "Not found", "Program with ID " + id + " not found")))
                .map(this::toResponse);
    }

@Override
public Mono<DistributionProgramResponse> save(DistributionProgramCreateRequest request) {
    return generateNextProgramCode()
        .flatMap(generatedCode -> {

            DistributionProgram program = DistributionProgram.builder()
                    .organizationId(request.getOrganizationId())
                    .programCode(generatedCode) // ← Usamos el código generado
                    .scheduleId(request.getScheduleId())
                    .routeId(request.getRouteId())
                    .programDate(LocalDate.parse(request.getProgramDate(), dateFormatter))
                    .plannedStartTime(request.getPlannedStartTime())
                    .plannedEndTime(request.getPlannedEndTime())
                    .actualStartTime(request.getActualStartTime())
                    .actualEndTime(request.getActualEndTime())
                    .status(request.getStatus())
                    .responsibleUserId(request.getResponsibleUserId())
                    .observations(request.getObservations())
                    .createdAt(Instant.now())
                    .build();

            return programRepository.save(program)
                    .map(this::toResponse);
        });
}

private static final String PROGRAM_PREFIX = "PROG";

private Mono<String> generateNextProgramCode() {
    return programRepository.findTopByOrderByProgramCodeDesc()
            .map(last -> {
                String lastCode = last.getProgramCode(); // Ej: "PROG007"
                int number = 0;
                try {
                    number = Integer.parseInt(lastCode.replace(PROGRAM_PREFIX, ""));
                } catch (NumberFormatException e) {
                    // Log o fallback
                }
                return String.format(PROGRAM_PREFIX + "%03d", number + 1);
            })
            .defaultIfEmpty(PROGRAM_PREFIX + "001");
}
    @Override
    public Mono<DistributionProgramResponse> update(String id, DistributionProgramCreateRequest request) {
        return programRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(
                        HttpStatus.NOT_FOUND.value(), "Not found", "Program with ID " + id + " not found")))
                .flatMap(existing -> {
                    existing.setPlannedStartTime(request.getPlannedStartTime());
                    existing.setPlannedEndTime(request.getPlannedEndTime());
                    existing.setActualStartTime(request.getActualStartTime());
                    existing.setActualEndTime(request.getActualEndTime());
                    existing.setStatus(request.getStatus());
                    existing.setObservations(request.getObservations());
                    existing.setResponsibleUserId(request.getResponsibleUserId());
                    return programRepository.save(existing);
                })
                .map(this::toResponse);
    }

    @Override
    public Mono<Void> delete(String id) {
        return programRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(
                        HttpStatus.NOT_FOUND.value(), "Not found", "Program with ID " + id + " not found")))
                .flatMap(programRepository::delete);
    }

    @Override
    public Mono<DistributionProgramResponse> changeStatus(String id, String status) {
        return programRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(
                        HttpStatus.NOT_FOUND.value(), "Not found", "Program with ID " + id + " not found")))
                .flatMap(program -> {
                    program.setStatus(status);
                    return programRepository.save(program);
                })
                .map(this::toResponse);
    }

    // Método de utilidad para mapear de entidad a DTO
    private DistributionProgramResponse toResponse(DistributionProgram program) {
        return DistributionProgramResponse.builder()
                .id(program.getId())
                .organizationId(program.getOrganizationId())
                .programCode(program.getProgramCode())
                .scheduleId(program.getScheduleId())
                .routeId(program.getRouteId())
                .programDate(program.getProgramDate() != null ? program.getProgramDate().format(dateFormatter) : null)
                .plannedStartTime(program.getPlannedStartTime())
                .plannedEndTime(program.getPlannedEndTime())
                .actualStartTime(program.getActualStartTime())
                .actualEndTime(program.getActualEndTime())
                .status(program.getStatus())
                .responsibleUserId(program.getResponsibleUserId())
                .observations(program.getObservations())
                .createdAt(program.getCreatedAt() != null ? program.getCreatedAt().toString() : null)
                .build();
    }
}
