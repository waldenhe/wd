package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author walden
 * @since 2017-10-08
 */
public class Recorddata extends Model<Recorddata> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 身份证号
     */
	private String cardId;
    /**
     * 刷卡时间
     */
	private String createTime;
	
	private Integer other1;
	
	private String name;
	
	private String teachername;
	
	
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    /**
	 * 所在食堂MAC
	 */
	private String other2;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getOther1() {
		return other1;
	}

	public void setOther1(Integer other1) {
		this.other1 = other1;
	}

	public String getOther2() {
		return other2;
	}

	public void setOther2(String other2) {
		this.other2 = other2;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
    public String toString() {
        return "Recorddata [id=" + id + ", cardId=" + cardId + ", createTime=" + createTime + ", other1=" + other1 + ", name="
                + name + ", teachername=" + teachername + ", other2=" + other2 + "]";
    }
}
