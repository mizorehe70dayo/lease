package com.mahiru.lease.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mahiru.lease.common.result.Result;
import com.mahiru.lease.model.entity.AttrKey;
import com.mahiru.lease.model.entity.AttrValue;
import com.mahiru.lease.web.admin.service.AttrKeyService;
import com.mahiru.lease.web.admin.service.AttrValueService;
import com.mahiru.lease.web.admin.vo.attr.AttrKeyVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "房间属性管理")
@RestController
@RequestMapping("/admin/attr")
@AllArgsConstructor
public class AttrController {
    private final AttrKeyService attrKeyService;

    private final AttrValueService attrValueService;

    @Operation(summary = "新增或更新属性名称")
    @PostMapping("key/saveOrUpdate")
    public Result saveOrUpdateAttrKey(@RequestBody AttrKey attrKey) {
        return Result.ok(attrKeyService.saveOrUpdate(attrKey));
    }

    @Operation(summary = "新增或更新属性值")
    @PostMapping("value/saveOrUpdate")
    public Result saveOrUpdateAttrValue(@RequestBody AttrValue attrValue) {
        return Result.ok(attrValueService.saveOrUpdate(attrValue));
    }

    @Operation(summary = "查询全部属性名称和属性值列表")
    @GetMapping("list")
    public Result<List<AttrKeyVo>> listAttrInfo() {
        return Result.ok(attrKeyService.getAttrInfoList());
    }

    @Operation(summary = "根据id删除属性名称")
    @DeleteMapping("key/deleteById")
    public Result removeAttrKeyById(@RequestParam Long attrKeyId) {
        attrKeyService.removeById(attrKeyId);
        attrValueService.remove(
                new LambdaQueryWrapper<AttrValue>()
                        .eq(AttrValue::getAttrKeyId, attrKeyId)
        );
        return Result.ok();
    }

    @Operation(summary = "根据id删除属性值")
    @DeleteMapping("value/deleteById")
    public Result removeAttrValueById(@RequestParam Long id) {
        return Result.ok(attrValueService.removeById(id));
    }
}
