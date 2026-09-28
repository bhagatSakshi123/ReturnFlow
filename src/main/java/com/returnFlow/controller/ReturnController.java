package com.returnFlow.controller;

import com.returnFlow.dto.CreateReturnRequest;
import com.returnFlow.entity.ReturnRequest;
import com.returnFlow.entity.Role;
import com.returnFlow.entity.User;
import com.returnFlow.repository.UserRepository;
import com.returnFlow.service.ReturnService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/returns")
public class ReturnController {

    private final ReturnService returnService;
    private final UserRepository userRepository;

    public ReturnController(
            ReturnService returnService,
            UserRepository userRepository) {

        this.returnService = returnService;
        this.userRepository = userRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ReturnRequest> createReturn(
            @RequestBody CreateReturnRequest request,
            Authentication authentication) {

        User customer = getLoggedInUser(authentication);

        ReturnRequest result = returnService.createReturn(
                request.getOrderItemId(),
                customer,
                request.getReason(),
                request.getDescription(),
                request.getEvidenceUrl()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    // CUSTOMER can see only their own return.
    // SELLER and ADMIN can see any return.
    @GetMapping("/{id}")
    public ReturnRequest getReturn(
            @PathVariable Long id,
            Authentication authentication) {

        User loggedInUser = getLoggedInUser(authentication);

        ReturnRequest returnRequest =
                returnService.getReturnById(id);

        if (loggedInUser.getRole() == Role.CUSTOMER &&
                !returnRequest.getCustomer()
                        .getId()
                        .equals(loggedInUser.getId())) {

            throw new RuntimeException(
                    "You cannot view another customer's return"
            );
        }

        return returnRequest;
    }

    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<ReturnRequest> getMyReturns(
            Authentication authentication) {

        User customer = getLoggedInUser(authentication);

        return returnService.getCustomerReturns(customer);
    }

    @GetMapping("/requested")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public List<ReturnRequest> getRequestedReturns() {
        return returnService.getRequestedReturns();
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public List<ReturnRequest> getActiveReturns() {
        return returnService.getActiveReturns();
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ReturnRequest approveReturn(@PathVariable Long id) {
        return returnService.approveReturn(id);
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ReturnRequest rejectReturn(
            @PathVariable Long id,
            @RequestParam String reason) {

        return returnService.rejectReturn(id, reason);
    }

    @PutMapping("/{id}/schedule-pickup")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ReturnRequest schedulePickup(@PathVariable Long id) {
        return returnService.schedulePickup(id);
    }

    @PutMapping("/{id}/received")
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    public ReturnRequest markReceived(@PathVariable Long id) {
        return returnService.markReceived(id);
    }

    private User getLoggedInUser(Authentication authentication) {

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Logged-in user not found"
                        ));
    }
}