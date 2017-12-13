package nw.orm.dao;

public class Paging {
	
	private int pageOffset;
	private int pageSize;
	
	private Paging(int pageOffset, int pageSize) {
		super();
		this.pageOffset = pageOffset;
		this.pageSize = pageSize;
	}
	
	public int getPageOffset() {
		return pageOffset;
	}
	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public static Paging paginate(int offset, int size) {
		return new Paging(offset, size);
	}
	
	public static Paging paginate(int size) {
		return new Paging(0, size);
	}

}
