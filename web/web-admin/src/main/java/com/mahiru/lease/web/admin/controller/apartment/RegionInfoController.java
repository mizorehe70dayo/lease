package com.mahiru.lease.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mahiru.lease.common.result.Result;
import com.mahiru.lease.model.entity.CityInfo;
import com.mahiru.lease.model.entity.DistrictInfo;
import com.mahiru.lease.model.entity.ProvinceInfo;
import com.mahiru.lease.web.admin.service.CityInfoService;
import com.mahiru.lease.web.admin.service.DistrictInfoService;
import com.mahiru.lease.web.admin.service.ProvinceInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "地区信息管理")
@RestController
@RequestMapping("/admin/region")
@AllArgsConstructor
public class RegionInfoController {
    private ProvinceInfoService provinceInfoService;
    private CityInfoService cityInfoService;
    private DistrictInfoService districtInfoService;

    @Operation(summary = "查询省份信息列表")
    @GetMapping("province/list")
    public Result<List<ProvinceInfo>> listProvince() {
        return Result.ok(provinceInfoService.list());
    }

    @Operation(summary = "根据省份id查询城市信息列表")
    @GetMapping("city/listByProvinceId")
    public Result<List<CityInfo>> listCityInfoByProvinceId(@RequestParam Long id) {
        return Result.ok(cityInfoService.list(
                new LambdaQueryWrapper<CityInfo>()
                        .eq(CityInfo::getProvinceId, id)
        ));
    }

    @GetMapping("district/listByCityId")
    @Operation(summary = "根据城市id查询区县信息")
    public Result<List<DistrictInfo>> listDistrictInfoByCityId(@RequestParam Long id) {
        return Result.ok(districtInfoService.list(
                new LambdaQueryWrapper<DistrictInfo>()
                        .eq(DistrictInfo::getCityId, id)
        ));
    }
}
