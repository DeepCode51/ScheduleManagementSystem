package com.test.project.system.richeng.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.common.exception.BusinessException;
import com.test.common.utils.security.ShiroUtils;
import com.test.common.utils.text.Convert;
import com.test.project.system.richeng.domain.TRicheng;
import com.test.project.system.richeng.mapper.TRichengMapper;
import com.test.project.system.richeng.service.ITRichengService;
import com.test.project.system.user.service.IUserService;

/**
 * 日程管理Service业务层处理
 * 
 * @author test
 * @date 2022-04-15
 */
@Service
public class TRichengServiceImpl implements ITRichengService {
	@Autowired
	private TRichengMapper tRichengMapper;

	@Autowired
	private IUserService userService;

	/**
	 * 查询日程管理
	 * 
	 * @param id 日程管理ID
	 * @return 日程管理
	 */
	@Override
	public TRicheng selectTRichengById(Long id) {
		return tRichengMapper.selectTRichengById(id);
	}

	/**
	 * 查询日程管理列表
	 * 
	 * @param tRicheng 日程管理
	 * @return 日程管理
	 */
	@Override
	public List<TRicheng> selectTRichengList(TRicheng tRicheng) {
		return tRichengMapper.selectTRichengList(tRicheng);
	}

	/**
	 * 新增日程管理
	 * 
	 * @param tRicheng 日程管理
	 * @return 结果
	 */
	@Override
	public int insertTRicheng(TRicheng tRicheng) {

		if (tRicheng.getUpdateBy().length() == 0) {

			TRicheng s = new TRicheng();
			s.setToUser(tRicheng.getToUser());
			List<TRicheng> list = selectTRichengList(s);
			long time0 = tRicheng.getBeginTime().getTime();
			long time1 = tRicheng.getEndTime().getTime();
			List<TRicheng> collect = list.stream().filter(e -> {
				long time = e.getBeginTime().getTime();
				long time2 = e.getEndTime().getTime();
				if ((time > time0 && time2 < time1) || // ------time0-- time------time2-----time1-------

				(time < time0 && time2 < time1 && time0 < time2) || // ------time-- time0------time2-----time1-------
				(time < time0 && time2 > time1) || // ------time-- time0------time1-----time2-------
				(time > time0 && time2 > time1 && time1 > time) // ------time0-- time------time1-----time2-------
				) {
					return true;
				}
				return false;
			}).collect(Collectors.toList());
			if (collect.size() > 0) {
				TRicheng tRicheng2 = collect.get(0);
				throw new BusinessException(
						"当前日程与已与" + tRicheng2.getCreateBy() + "创建的：" + tRicheng2.getContent() + "冲突！是否继续添加");
			}
		}

		tRicheng.setCreateBy(ShiroUtils.getLoginName());

		return tRichengMapper.insertTRicheng(tRicheng);
	}

	/**
	 * 修改日程管理
	 * 
	 * @param tRicheng 日程管理
	 * @return 结果
	 */
	@Override
	public int updateTRicheng(TRicheng tRicheng) {
		return tRichengMapper.updateTRicheng(tRicheng);
	}

	/**
	 * 删除日程管理对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteTRichengByIds(String ids) {
		return tRichengMapper.deleteTRichengByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除日程管理信息
	 * 
	 * @param id 日程管理ID
	 * @return 结果
	 */
	@Override
	public int deleteTRichengById(Long id) {
		return tRichengMapper.deleteTRichengById(id);
	}
}
