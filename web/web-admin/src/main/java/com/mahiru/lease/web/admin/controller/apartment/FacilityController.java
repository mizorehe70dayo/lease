package com.mahiru.lease.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mahiru.lease.common.result.Result;
import com.mahiru.lease.model.entity.FacilityInfo;
import com.mahiru.lease.model.enums.ItemType;
import com.mahiru.lease.web.admin.service.FacilityInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "配套管理")
@RestController
@RequestMapping("/admin/facility")
@AllArgsConstructor
public class FacilityController {
    private final FacilityInfoService facilityInfoService;

    @Operation(summary = "[根据类型]查询配套信息列表")
    @GetMapping("list")
    public Result<List<FacilityInfo>> listFacility(@RequestParam(required = false) ItemType type) {
        return Result.ok(facilityInfoService.list(
                new LambdaQueryWrapper<FacilityInfo>()
                        .eq(type != null, FacilityInfo::getType, type)
        ));
    }

    @Operation(summary = "新增或修改配套信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody FacilityInfo facilityInfo) {
        return Result.ok(facilityInfoService.saveOrUpdate(facilityInfo));
    }

    @Operation(summary = "根据id删除配套信息")
    @DeleteMapping("deleteById")
    public Result removeFacilityById(@RequestParam Long id) {
        return Result.ok(facilityInfoService.removeById(id));
    }
}
