package com.mahiru.lease.web.admin.controller.apartment;


import com.mahiru.lease.common.result.Result;
import com.mahiru.lease.model.entity.LeaseTerm;
import com.mahiru.lease.web.admin.service.LeaseTermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "租期管理")
@RequestMapping("/admin/term")
@RestController
@AllArgsConstructor
public class LeaseTermController {
    private final LeaseTermService leaseTermService;

    @GetMapping("list")
    @Operation(summary = "查询全部租期列表")
    public Result<List<LeaseTerm>> listLeaseTerm() {
        return Result.ok(leaseTermService.list());
    }

    @PostMapping("saveOrUpdate")
    @Operation(summary = "保存或更新租期信息")
    public Result saveOrUpdate(@RequestBody LeaseTerm leaseTerm) {
        return Result.ok(leaseTermService.saveOrUpdate(leaseTerm));
    }

    @DeleteMapping("deleteById")
    @Operation(summary = "根据ID删除租期")
    public Result deleteLeaseTermById(@RequestParam Long id) {
        return Result.ok(leaseTermService.removeById(id));
    }
}
