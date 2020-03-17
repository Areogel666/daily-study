package cn.lxr.dataStructure.arrayList;

public class OrderArrayObject<T> extends ArrayObject<T> {

	/**
	 * 根据内容查找(二分法)
	 * 返回下标
	 */
	@Override
	public int find(T t) {
		int minKey = 0;
		int maxKey = size() - 1;
		int compareKey;
		while (true) {
			compareKey = (minKey + maxKey) / 2;
			if (compare(arr[compareKey], t) == 0) {
				return compareKey;
			} else if (maxKey < minKey) { //未查到
				return -1;
			} else if (compare(arr[compareKey], t) > 0) {
				maxKey = compareKey - 1;
			} else {
				minKey = compareKey + 1;
			}
		}
	}
	
	/**
	 * 删除
	 */
	@Override
	public void delete(T t) {
		int delKey = this.find(t);
		if (delKey < 0) {
			return;
		}
		for (int i = delKey; i < nElems; i++) {
			arr[i] = arr[i + 1];
		}
		nElems--;
	}
}
