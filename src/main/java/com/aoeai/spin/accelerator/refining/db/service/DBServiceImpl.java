package com.aoeai.spin.accelerator.refining.db.service;

import cn.hutool.core.date.DateTime;
import com.aoeai.spin.accelerator.admin.vo.ColumnVO;
import com.aoeai.spin.accelerator.admin.vo.TableVO;
import com.aoeai.spin.accelerator.refining.db.bean.Column;
import com.aoeai.spin.accelerator.refining.db.bean.Table;
import com.aoeai.spin.accelerator.refining.db.mapper.DBMapper;
import com.aoeai.spin.accelerator.refining.db.mapper.MySQLMapper;
import com.aoeai.spin.accelerator.refining.db.po.ColumnPO;
import com.aoeai.spin.accelerator.refining.db.po.TablePO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author aoe
 * @date 2020/6/7
 */
@Service
public class DBServiceImpl implements DBService {

    private DBMapper dbMapper;

    @Resource
    private MySQLMapper mySQLMapper;

    @PostConstruct
    private void init(){
        dbMapper = mySQLMapper;
    }

    @Override
    public List<TableVO> getTableList(String tableName) {
        List<TablePO> tableList = dbMapper.selectTableList(tableName);
        List<TableVO> voList = new ArrayList<>(tableList.size());
        for (TablePO tablePO : tableList) {
            TableVO vo = new TableVO();
            BeanUtils.copyProperties(tablePO, vo);
            vo.setCreateTime(DateTime.of(tablePO.getCreateTime()).toStringDefaultTimeZone());
            voList.add(vo);
        }
//        List list = getColumnList(tableName);
        return voList;
    }

    @Override
    public List<ColumnVO> getColumnList(String tableName) {
        List<ColumnPO> columnList = dbMapper.selectColumnList(tableName);
        List<ColumnVO> voList = new ArrayList<>(columnList.size());
        for (ColumnPO columnPO : columnList) {
            ColumnVO vo = new ColumnVO();
            BeanUtils.copyProperties(columnPO, vo);

            if ("bigint".equals(columnPO.getDataType())) {
                String lengthStr = columnPO.getColumnType()
                        .replace("bigint(", "")
                        .replace(")", "");
                vo.setIntegersLength(Integer.parseInt(lengthStr));
            }

            voList.add(vo);
        }
        return voList;
    }

    @Override
    public Table getTable(String tableName) {
        TablePO tablePO = dbMapper.selectTable(tableName);
        Table table = new Table();
        table.setName(tableName);
        table.setComment(tablePO.getTableComment());

        List<ColumnVO> columnVOList =  getColumnList(tableName);
        List<Column> columnList = new ArrayList<>(columnVOList.size());
        for (ColumnVO vo : columnVOList) {
            Column column = new Column();
            column.setName(vo.getColumnName());
            column.setComment(vo.getColumnComment());
            column.setType(vo.getDataType());
            column.setPrimaryKey("PRI".equals(vo.getColumnKey()));
            column.setLength(vo.getIntegersLength());

            columnList.add(column);
        }
        table.setColumns(columnList);

        return table;
    }
}
