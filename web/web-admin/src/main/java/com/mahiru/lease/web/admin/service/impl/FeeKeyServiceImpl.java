package com.mahiru.lease.web.admin.service.impl;

import com.mahiru.lease.model.entity.FeeKey;
import com.mahiru.lease.web.admin.mapper.FeeKeyMapper;
import com.mahiru.lease.web.admin.service.FeeKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mahiru.lease.web.admin.vo.fee.FeeKeyVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【fee_key(杂项费用名称表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
@AllArgsConstructor
public class FeeKeyServiceImpl extends ServiceImpl<FeeKeyMapper, FeeKey>
    implements FeeKeyService{
    private final FeeKeyMapper feeKeyMapper;

    @Override
    public List<FeeKeyVo> getFeeInfoList() {
        return feeKeyMapper.selectFeeInfoList();
    }
}




