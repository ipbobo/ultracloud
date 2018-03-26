package com.cmp.service.servicemgt;

import java.math.BigInteger;
import java.util.List;

import com.cmp.entity.MirrorTemplateMap;
import com.cmp.sid.CmpDict;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 镜像管理业务层接口
 * 
 * @author liuweixing
 *
 */
public interface MirrorService {

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void saveTemplate(PageData pd) throws Exception;

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception;

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listTemplateByType(PageData pd) throws Exception;

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd) throws Exception;

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception;

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception;

	/**
	 * 列出已加入镜像的模板
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllInByMirrorId(PageData pd) throws Exception;

	/**
	 * 列出未加入镜像的模板
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllOutByMirrorId(PageData pd) throws Exception;

	/**
	 * 查询镜像下的模板
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<MirrorTemplateMap> listMirrorTemplateMap(PageData pd) throws Exception;

	/**
	 * 批量删除镜像与模板关联
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAllMirrorTemplateMap(List<BigInteger> list) throws Exception;

	/**
	 * 批量插入镜像与模板关联
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void insertAllMirrorTemplateMap(List<MirrorTemplateMap> list) throws Exception;

	/**
	 * 批量删除镜像与模板关联,按镜像
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteByMirrorId(BigInteger id) throws Exception;

	// 模板列表查询
	public List<CmpDict> getImgList(PageData pd) throws Exception;
}