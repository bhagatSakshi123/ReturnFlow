package com.returnFlow.service;

import com.returnFlow.entity.*;
import com.returnFlow.repository.OrderItemRepository;
import com.returnFlow.repository.ReturnRequestRepository;
import com.returnFlow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReturnService {

    private final ReturnRequestRepository returnRequestRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    public ReturnService(
            ReturnRequestRepository returnRequestRepository,
            OrderItemRepository orderItemRepository,
            UserRepository userRepository) {

        this.returnRequestRepository = returnRequestRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
    }

    public ReturnRequest createReturn(
            Long orderItemId,
            User customer,
            ReturnReason reason,
            String description,
            String evidenceUrl) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        Order order = orderItem.getOrder();

        if (!order.getCustomer().getId().equals(customer.getId())) {
            throw new RuntimeException(
                    "You cannot return another customer's order");
        }

        if (order.getStatus() != OrderStatus.DELIVERED) {
            throw new RuntimeException(
                    "Only delivered orders can be returned");
        }

        Product product = orderItem.getProduct();

        if (!product.isReturnable()) {
            throw new RuntimeException(
                    "This product is not returnable");
        }

        LocalDateTime deliveryDate = order.getDeliveryDate();

        if (deliveryDate == null) {
            throw new RuntimeException(
                    "Delivery date is missing");
        }

        LocalDateTime returnDeadline =
                deliveryDate.plusDays(product.getReturnWindowDays());

        if (LocalDateTime.now().isAfter(returnDeadline)) {
            throw new RuntimeException(
                    "Return window has expired");
        }

        boolean alreadyReturned =
                returnRequestRepository.existsByOrderItemAndStatusNot(
                        orderItem,
                        ReturnStatus.REJECTED);

        if (alreadyReturned) {
            throw new RuntimeException(
                    "An active return already exists for this product");
        }

        ReturnRequest returnRequest = new ReturnRequest();

        returnRequest.setOrderItem(orderItem);
        returnRequest.setCustomer(customer);
        returnRequest.setReason(reason);
        returnRequest.setDescription(description);
        returnRequest.setEvidenceUrl(evidenceUrl);
        returnRequest.setRequestDate(LocalDateTime.now());
        returnRequest.setStatus(ReturnStatus.REQUESTED);

        return returnRequestRepository.save(returnRequest);
    }

    public ReturnRequest getReturnById(Long id) {

        return returnRequestRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Return request not found"));
    }

    public List<ReturnRequest> getCustomerReturns(User customer) {
        return returnRequestRepository.findByCustomer(customer);
    }

    // NEW
    public List<ReturnRequest> getRequestedReturns() {

        return returnRequestRepository
                .findByStatus(ReturnStatus.REQUESTED);
    }

    public List<ReturnRequest> getActiveReturns() {

        return returnRequestRepository.findByStatusIn(
                List.of(
                        ReturnStatus.REQUESTED,
                        ReturnStatus.APPROVED,
                        ReturnStatus.PICKUP_SCHEDULED,
                        ReturnStatus.RECEIVED,
                        ReturnStatus.INSPECTION_COMPLETED,
                        ReturnStatus.REFUND_INITIATED
                )
        );
    }

    public ReturnRequest approveReturn(Long id) {

        ReturnRequest request = getReturnById(id);

        if (request.getStatus() != ReturnStatus.REQUESTED) {
            throw new RuntimeException(
                    "Only requested returns can be approved");
        }

        request.setStatus(ReturnStatus.APPROVED);

        return returnRequestRepository.save(request);
    }

    public ReturnRequest rejectReturn(
            Long id,
            String rejectionReason) {

        ReturnRequest request = getReturnById(id);

        if (request.getStatus() != ReturnStatus.REQUESTED) {
            throw new RuntimeException(
                    "Only requested returns can be rejected");
        }

        request.setStatus(ReturnStatus.REJECTED);
        request.setRejectionReason(rejectionReason);

        return returnRequestRepository.save(request);
    }

    public ReturnRequest schedulePickup(Long id) {

        ReturnRequest request = getReturnById(id);

        if (request.getStatus() != ReturnStatus.APPROVED) {
            throw new RuntimeException(
                    "Return must be approved before pickup scheduling");
        }

        request.setStatus(ReturnStatus.PICKUP_SCHEDULED);

        return returnRequestRepository.save(request);
    }

    public ReturnRequest markReceived(Long id) {

        ReturnRequest request = getReturnById(id);

        if (request.getStatus() != ReturnStatus.PICKUP_SCHEDULED) {
            throw new RuntimeException(
                    "Product must have pickup scheduled first");
        }

        request.setStatus(ReturnStatus.RECEIVED);

        return returnRequestRepository.save(request);
    }
}