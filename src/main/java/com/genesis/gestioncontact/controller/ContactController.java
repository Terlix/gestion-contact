package com.genesis.gestioncontact.controller;

import com.genesis.gestioncontact.dto.ContactDto;
import com.genesis.gestioncontact.service.contact.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Contact Controller", description = "Endpoints for managing contacts")
@RestController
@RequestMapping("/api/contacts")
@Validated
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @Operation(summary = "Get all contacts", description = "This endpoint is used to retrieve all the contacts")
    @GetMapping
    public List<ContactDto> retrieveAllContacts() {
        return contactService.getAllContacts();
    }

    @Operation(summary = "Create a contact", description = "This endpoint is used to create a contact")
    @PostMapping
    public ResponseEntity<?> addContact(@RequestBody ContactDto contactDto) {
        return contactService.createContact(contactDto)
                .map(contact -> ResponseEntity.status(HttpStatus.CREATED).build())
                .orElseGet(() -> ResponseEntity.badRequest().body("A freelance contact must have a TVA Number."));
    }

    @Operation(summary = "Add contact to a company", description = "This endpoint is used to add a contact to a company")
    @PostMapping("/add-to-company")
    public ResponseEntity<ContactDto> addContactToCompany(
            @Parameter(description = "ID of the contact to add to the company") @RequestParam Long contactId,
            @Parameter(description = "ID of the company where the contact should be added") @RequestParam Long companyId
    ) {
        Optional<ContactDto> contact = contactService.addContactToCompany(contactId, companyId);
        return contact.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update a contact", description = "This endpoint is used to update a contact")
    @PutMapping("/{id}")
    public ResponseEntity<ContactDto> modifyContact(
            @Parameter(description = "ID of the contact to update") @PathVariable Long id,
            @Valid @RequestBody ContactDto contactDto) {
        return contactService.updateContact(id, contactDto).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a contact", description = "This endpoint is used to delete the contact")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@Parameter(description = "ID of the contact to delete") @PathVariable Long id) {
        contactService.deleteContact(id);
    }
}