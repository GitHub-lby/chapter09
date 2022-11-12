package houserent.service;

import houserent.domain.House;

/**
 * HouseService.java<=>类[业务层]
 * //定义House[],保存House对象
 * 1. 响应HouseView的调用
 * 2. 完成对房屋信息的各种操作(增删改查c[create]r[read]u[update]d[delete])
 */
@SuppressWarnings({"all"})
public class HouseService {

    private House[] houses; //保存House对象
    private int houseNums = 1;//记录当前有多少个房屋信息
    private int idCounter = 1;//记录当前的id增长到哪个值了

    //构造器
    public HouseService(int size) {
        //new houses
        houses = new House[size];//当创建HouseService对象，指定数组大小
        //为了配合测试列表信息，老师这里初始化一个House对象
        houses[0] = new House(1, "jack", "112", "海淀区", 2000, "未出租");
    }

    //findById方法，查找房屋信息
    public House findById(int findId) {

        //应当先找到我们要查找的房屋信息对应的下标
        for (int i = 0; i < houseNums; i++) {
            if (findId == houses[i].getId()) {
                return houses[i];
            }
        }
        return null;
    }

    //del方法，删除一个房屋信息
    public boolean del(int delId) {

        //应当先找到删除的房屋信息对应的下标
        //强调，一定要搞清楚 下标和房屋的编号不是一回事
        int index = -1;//标记法
        for (int i = 0; i < houseNums; i++) {
            if (delId == houses[i].getId()) {//要删除的房屋(id),是数组下标为i的元素
                index = i;//就使用index记录i
            }
        }

        if (index == -1) {//说明delId在数组中不存在
            return false;
        }
        //如果找到，这里需要小伙伴动脑筋，有点难，怎么删除？
        //老师思路分析:
        for (int i = index; i < houseNums - 1; i++) {
            houses[i] = houses[i + 1];
        }
        //把当前存在的房屋信息的最后一个 设置为null
        houses[--houseNums] = null;
        return true;

    }

    //add方法，添加新对象，返回boolean
    public boolean add(House newHouse) {
        //判断是否还可以继续添加(我们暂时不考虑数组扩容的问题) => 能否自己加入数组扩容机制？
        if (houseNums == houses.length) {//不能再添加了
            System.out.println("数组已满，不能再添加了...");
            return false;
        }
        //把newHouse对象加到,新增了一个房屋
        houses[houseNums++] = newHouse;
        //我们程序员需要设置一个id自增长的机制,然后更新newHouse的id
        newHouse.setId(++idCounter);
        return true;
    }

    //list方法，返回houses数组
    public House[] list() {
        return houses;
    }
}
