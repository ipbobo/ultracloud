package com.cmp.service.servicemgt;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.entity.MirrorTemplateMap;
import com.cmp.entity.UserGroupUserMap;
import com.cmp.sid.CmpDict;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 镜像业务层实现类
 * 
 * @author liuweixing
 *
 */
@Service("mirrorService")
public class MirrorServiceImpl implements MirrorService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("MirrorMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("MirrorMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("MirrorMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("MirrorMapper.datalistPage", page);
	}
	
	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listTemplateByType(Page page) throws Exception {
		return (List<PageData>) dao.findForList("MirrorMapper.listTemplateByType", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MirrorMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MirrorMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("MirrorMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 列出已加入镜像的模板
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllInByMirrorId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MirrorMapper.listAllInByMirrorId", pd);
	}

	/**
	 * 列出未加入镜像的模板
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllOutByMirrorId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MirrorMapper.listAllOutByMirrorId", pd);
	}

	/**
	 * 查询镜像下的模板
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<MirrorTemplateMap> listMirrorTemplateMap(PageData pd) throws Exception {
		return (List<MirrorTemplateMap>) dao.findForList("MirrorMapper.listMirrorTemplateMap", pd);
	}

	/**
	 * 批量删除镜像与模板关联
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAllMirrorTemplateMap(List<BigInteger> list) throws Exception {
		dao.delete("MirrorMapper.deleteAllMirrorTemplateMap", list);
	}
	
	/**批量插入镜像与模板关联
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void insertAllMirrorTemplateMap(List<MirrorTemplateMap> list) throws Exception {
		dao.batchSave("MirrorMapper.insertAllMirrorTemplateMap", list);
	}
	
	/**批量删除镜像与模板关联,按镜像
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteByMirrorId(BigInteger id) throws Exception {
		dao.delete("MirrorMapper.deleteByMirrorId", id);
	}

	//模板列表查询
	@SuppressWarnings("unchecked")
	public List<CmpDict> getImgList() throws Exception {
		return (List<CmpDict>) dao.findForList("MirrorMapper.getImgList", null);
	}
}