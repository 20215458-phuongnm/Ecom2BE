package com.mygame.controller.address;//package com.mygame.controller.address;
//
//import com.mygame.constant.AppConstants;
//import com.mygame.dto.address.ProvinceRequest;
//import com.mygame.dto.address.ProvinceResponse;
//import com.mygame.service.address.ProvinceService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.lang.Nullable;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/provinces")
//@AllArgsConstructor
//public class ProvinceController {
//
//    private ProvinceService provinceService;
//
////    @GetMapping
////    public ResponseEntity<ListWrapperResponse> getAllProvinces(
////            @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
////            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
////            @RequestParam(name = "sort", defaultValue = AppConstants.DEFAULT_SORT) String sort,
////            @RequestParam(name = "filter", required = false) @Nullable String filter,
////            @RequestParam(name = "search", required = false) @Nullable String search,
////            @RequestParam(name = "all", required = false) boolean all
////    ) {
////        return ResponseEntity.status(HttpStatus.OK).body(provinceService.findAll(page, size, sort, filter, search, all));
////    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ProvinceResponse> getProvince(@PathVariable("id") Long id) {
//        return ResponseEntity.status(HttpStatus.OK).body(provinceService.findById(id));
//    }
//
//    @PostMapping
//    public ResponseEntity<ProvinceResponse> createProvince(@RequestBody ProvinceRequest provinceRequest) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(provinceService.save(provinceRequest));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ProvinceResponse> updateProvince(@PathVariable("id") Long id,
//                                                           @RequestBody ProvinceRequest provinceRequest) {
//        return ResponseEntity.status(HttpStatus.OK).body(provinceService.save(id, provinceRequest));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProvince(@PathVariable("id") Long id) {
//        provinceService.delete(id);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Void> deleteProvinces(@RequestBody List<Long> ids) {
//        provinceService.delete(ids);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//
//}
