package com.mahiru.lease.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mahiru.lease.common.result.Result;
import com.mahiru.lease.model.entity.ApartmentInfo;
import com.mahiru.lease.model.enums.ReleaseStatus;
import com.mahiru.lease.web.admin.service.ApartmentInfoService;
import com.mahiru.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.mahiru.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.mahiru.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.mahiru.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "公寓信息管理")
@RestController
@RequestMapping("/admin/apartment")
@AllArgsConstructor
public class ApartmentController {
    private final ApartmentInfoService apartmentInfoService;

    @Operation(summary = "保存或更新公寓信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ApartmentSubmitVo apartmentSubmitVo) {
        apartmentInfoService.saveOrUpdateApartmentInfo(apartmentSubmitVo);
        return Result.ok();
    }

    @Operation(summary = "根据条件分页查询公寓列表")
    @GetMapping("pageItem")
    public Result<IPage<ApartmentItemVo>> pageItem(@RequestParam long current,
                                                   @RequestParam long size,
                                                   ApartmentQueryVo apartmentQueryVo) {
        return Result.ok(apartmentInfoService.pageApartmentItemByQueryVo(
                new Page<ApartmentItemVo>(current, size), apartmentQueryVo
        ));
    }

    @Operation(summary = "根据ID获取公寓详细信息")
    @GetMapping("getDetailById")
    public Result<ApartmentDetailVo> getDetailById(@RequestParam Long id) {
        return Result.ok(apartmentInfoService.getApartmentDetailById(id));
    }

    @Operation(summary = "根据id删除公寓信息")
    @DeleteMapping("removeById")
    public Result removeById(@RequestParam Long id) {
        apartmentInfoService.removeApartmentInfoById(id);
        return Result.ok();
    }

    @Operation(summary = "根据id修改公寓发布状态")
    @PostMapping("updateReleaseStatusById")
    public Result updateReleaseStatusById(@RequestParam Long id, @RequestParam ReleaseStatus status) {
        return Result.ok(apartmentInfoService.update(new LambdaUpdateWrapper<ApartmentInfo>()
                .eq(ApartmentInfo::getId, id)
                .set(ApartmentInfo::getIsRelease, status)));
    }

    @Operation(summary = "根据区县id查询公寓信息列表")
    @GetMapping("listInfoByDistrictId")
    public Result<List<ApartmentInfo>> listInfoByDistrictId(@RequestParam Long id) {
        return Result.ok(apartmentInfoService.list(new LambdaQueryWrapper<ApartmentInfo>()
                .eq(ApartmentInfo::getDistrictId, id)));
    }

    @Operation(summary = "根据城市id查询公寓信息列表")
    @GetMapping("listInfoByCityId")
    public Result<List<ApartmentInfo>> listInfoByCityId(@RequestParam Long id) {
        return Result.ok(apartmentInfoService.list(new LambdaQueryWrapper<ApartmentInfo>()
                .eq(ApartmentInfo::getCityId, id)));
    }

    @Operation(summary = "根据省份id查询公寓信息列表")
    @GetMapping("listInfoByProvinceId")
    public Result<List<ApartmentInfo>> listInfoByProvinceId(@RequestParam Long id) {
        return Result.ok(apartmentInfoService.list(new LambdaQueryWrapper<ApartmentInfo>()
                .eq(ApartmentInfo::getProvinceId, id)));
    }

    @Operation(summary = "根据省、市、区县查询公寓信息列表")
    @GetMapping("listInfo")
    public Result<List<ApartmentInfo>> listInfoByLocation(
            @RequestParam(required = false) Long provinceId,
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) Long districtId) {

        LambdaQueryWrapper<ApartmentInfo> queryWrapper = new LambdaQueryWrapper<>();

        if (provinceId != null) {
            queryWrapper.eq(ApartmentInfo::getProvinceId, provinceId);
        }
        if (cityId != null) {
            queryWrapper.eq(ApartmentInfo::getCityId, cityId);
        }
        if (districtId != null) {
            queryWrapper.eq(ApartmentInfo::getDistrictId, districtId);
        }

        return Result.ok(apartmentInfoService.list(queryWrapper));
    }
}














