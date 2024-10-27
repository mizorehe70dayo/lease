package com.mahiru.lease.web.admin.service.impl;

import com.mahiru.lease.model.entity.AttrKey;
import com.mahiru.lease.web.admin.mapper.AttrKeyMapper;
import com.mahiru.lease.web.admin.service.AttrKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mahiru.lease.web.admin.vo.attr.AttrKeyVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【attr_key(房间基本属性表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
@AllArgsConstructor
public class AttrKeyServiceImpl extends ServiceImpl<AttrKeyMapper, AttrKey>
    implements AttrKeyService{
    private final AttrKeyMapper attrKeyMapper;

    @Override
    public List<AttrKeyVo> getAttrInfoList() {
        return attrKeyMapper.selectAttrInfoList();
    }
}




