package ntu.csie.selab.inventorysystem.controller;

import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.Acquisition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ntu.csie.selab.inventorysystem.service.AcquisitionService;
import model.DatabaseObject;

import javax.validation.Valid;

@RestController
@RequestMapping("/sync")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class SyncController {
    // @Autowired
    AcquisitionService acquisitionService = new AcquisitionService();

    // @PostMapping(value = "/acquisition", produces = "application/json")
    // public void newAcquisition(
    //         @Valid @RequestBody Acquisition.AcquisitionValidation acquisitionValidation,
    //         BindingResult validation
    // ) {
    //     if (validation.getFieldErrorCount() > 0)
    //         throw new UnprocessableEntityException(String.format("Invalid field value: %s",
    //                 validation.getFieldErrors().get(0).getField()));
    //     Acquisition acquisition = acquisitionService.newAcquisition(acquisitionValidation);
    // }

    @PostMapping(value = "/acquisition", produces = "application/json")
    public DatabaseObject newAcquisition(
        @Valid @RequestBody Acquisition.AcquisitionValidation acquisitionValidation,
        BindingResult validation
    ) {
        if (validation.getFieldErrorCount() > 0)
            throw new UnprocessableEntityException(String.format("Invalid field value: %s",
                    validation.getFieldErrors().get(0).getField()));
        return acquisitionService.newAcquisition(acquisitionValidation);
    }
}
