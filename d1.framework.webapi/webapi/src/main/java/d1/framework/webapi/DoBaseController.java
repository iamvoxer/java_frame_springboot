package d1.framework.webapi;

import d1.framework.webapi.http.Result;
import d1.framework.webapi.http.ResultUtil;
import d1.framework.webapi.service.impl.DoServiceImpBase;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//Controller的基类，封装一些通用增删改查
public abstract class DoBaseController<T> {

    private static final Logger logger = LoggerFactory.getLogger(DoBaseController.class);

    abstract protected DoServiceImpBase<T> getBaseService();

    @ApiOperation(value = "获取数据详细信息", notes = "根据id来获取详细信息")
    @ApiImplicitParam(name = "id", value = "数据ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<T> findById(@PathVariable String id) {
        T mUser = getBaseService().findById(id);
        return mUser != null ? ResultUtil.success("获取详细信息", mUser) : ResultUtil.fail("数据为空或获取失败!");
    }

    /**
     * 通常需要在分页前先排序,子类重写这个方法
     *
     * @return
     */
    protected Sort withSort() {
        return null;
    }

    public Result<Page<T>> findAllByParams(Integer page, Integer size, @RequestBody(required = false) T params) {
        Page<T> result = getBaseService().findAllWithPage(page, size, withSort(), params);
        return ResultUtil.success("获取所有数据", result);
    }

    public Result insert(@RequestBody T opt) {
        try {
            getBaseService().insert(opt);
            return ResultUtil.success("创建成功");
        } catch (Exception e) {
            logger.error("insert/" + opt, e);
            return ResultUtil.fail("创建失败:", e);
        }
    }

    public Result update(@RequestBody T opt) {
        try {
            getBaseService().update(opt);
            return ResultUtil.success("更新成功");
        } catch (Exception e) {
            logger.error("update/" + opt, e);
            return ResultUtil.fail("更新失败:", e);
        }
    }

    @ApiOperation(value = "删除数据", notes = "根据id来指定删除数据")
    @ApiImplicitParam(name = "id", value = "角色ID", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        try {
            getBaseService().delete(id);
        } catch (Exception e) {
            logger.error("delete/" + id, e);
            return ResultUtil.fail("删除失败:", e);
        }
        return ResultUtil.success("删除成功!");
    }
}
