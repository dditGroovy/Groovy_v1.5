package kr.co.groovy.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
public class PageVO {
    private Long page;
    private Long perPage;
    private Long totalPage;
    private Long startRow;
    private Long lastRow;
    private Long block;
    private Long perBlock;
    private Long startNum;
    private Long lastNum;
    private boolean pre;
    private boolean next;
    private String depCode;
    private String sortBy;
    private String emplNm;

    public PageVO() {
        this.page=1L;
        this.perPage=10L;
        this.perBlock=5L;
    }

    public void setRow() {
        this.startRow =  (this.getPage()-1)*this.getPerPage();
        this.lastRow = this.getPage()*this.getPerPage();
    }

    public void setNum(Long totalCount) {
        this.totalPage = totalCount%this.getPerPage()==0 ? totalCount/this.getPerPage() : totalCount/this.getPerPage()+1;
        Long totalBlock = totalPage%this.getPerBlock()==0 ? totalPage/this.getPerBlock() : totalPage/this.getPerBlock()+1;

        Long curBlock = this.getPage()%this.getPerBlock()==0 ? this.getPage()/this.getPerBlock() : this.getPage()/this.getPerBlock()+1;

        this.startNum= (curBlock-1)*this.getPerBlock() + 1;
        this.lastNum= curBlock*this.getPerBlock();

        if(curBlock==totalBlock) {
            this.lastNum=totalPage;
        }
        if(this.page>1) {
            pre=true;
        }else{
            pre=false;
        }
        if(curBlock<totalBlock) {
            next=true;
        }else{
            next=false;
        }

    }

    public Long getPerPage() {
        if(this.perPage==null) {
            this.perPage=10L;
        }
        return perPage;
    }

    public Long getPage() {
        if(this.page==null || this.page<=0) {
            this.page=1L;
        }
        return page;
    }

}