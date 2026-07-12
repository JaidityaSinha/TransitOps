package com.transitops.backend.controller;

import com.transitops.backend.entity.MaintenanceLog;
import com.transitops.backend.service.MaintenanceLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceLogController {

    private final MaintenanceLogService service;

    public MaintenanceLogController(MaintenanceLogService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','SAFETY_OFFICER','FLEET_MANAGER','FINANCIAL_ANALYST')")
    @GetMapping
    public List<MaintenanceLog> getAllLogs() {
        return service.getAllLogs();
    }

    @PreAuthorize("hasAnyRole('ADMIN','SAFETY_OFFICER','FLEET_MANAGER','FINANCIAL_ANALYST')")
    @GetMapping("/{id}")
    public MaintenanceLog getLog(@PathVariable Long id) {
        return service.getLogById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SAFETY_OFFICER')")
    @PostMapping
    public MaintenanceLog createLog(@RequestBody MaintenanceLog log) {
        return service.saveLog(log);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SAFETY_OFFICER')")
    @PutMapping("/{id}")
    public MaintenanceLog updateLog(@PathVariable Long id,
                                    @RequestBody MaintenanceLog log) {
        return service.updateLog(id, log);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteLog(@PathVariable Long id) {
        service.deleteLog(id);
    }
}