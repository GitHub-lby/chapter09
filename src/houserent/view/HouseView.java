package houserent.view;

import houserent.domain.House;
import houserent.service.HouseService;
import houserent.utils.Utility;

/**
 * 1.显示界面
 * 2.接收用户的输入
 * 调用HouseService完成对房屋信息的各种操作
 */
@SuppressWarnings({"all"})
public class HouseView {

    private boolean loop = true;//控制显示菜单
    private char key = ' ';//接收用户选择
    private HouseService houseService = new HouseService(2);//设置数组大小为10


    //根据id修改房屋信息
    public void update() {
        System.out.println("=============修改房屋信息==============");
        System.out.println("请选择待修改房屋编号(-1表示退出)");
        int updateId = Utility.readInt();
        if (updateId == -1) {
            System.out.println("=============你放弃修改房屋信息==============");
            return;
        }

        //根据输入得到update，查找对象

        //提示：返回的是引用类型
        //因此修改的元素就是house数组的元素！！！！
        House house = houseService.findById(updateId);
        if (house == null) {
            System.out.println("=============修改房屋信息编号不存在===============");
            return;
        }

        System.out.print("姓名(" + house.getName() + "): ");
        String name = Utility.readString(8, "");//这里如果用户直接回车表示不修改信息，默认""
        if (!"".equals(name)) {//修改
            house.setName(name);
        }

        System.out.print("电话(" + house.getPhone() + "): ");
        String phone = Utility.readString(12, "");
        if (!"".equals(phone)) {//修改
            house.setPhone(phone);
        }

        System.out.print("地址(" + house.getAddress() + "): ");
        String address = Utility.readString(18, "");
        if (!"".equals(address)) {
            house.setAddress(address);
        }

        System.out.print("租金(" + house.getRent() + "): ");
        int rent = Utility.readInt(-1);
        if (!"".equals(rent)) {
            house.setRent(rent);
        }

        System.out.print("状态(" + house.getState() + "): ");
        String state = Utility.readString(3, "");
        if (!"".equals(state)) {
            house.setState(state);
        }
        System.out.print("=============修改房屋信息成功=============");

    }

    //编写findHouse() 接收输入id，调用Service 的find方法
    public void findHouse() {
        System.out.println("=============查找房屋信息===============");
        System.out.print("请输入你要查找的id:");
        int findId = Utility.readInt();
        //调用方法
        House house = houseService.findById(findId);
        if (house != null) {//如果不为null，也就是有这个房屋信息，输出信息
            System.out.println(house);
        } else {
            System.out.println("=============查找的房屋信息不存在=============");
        }
    }

    //完成退出确认
    public void exit() {
        //这里使用Utility提供的方法，完成确认
        char c = Utility.readConfirmSelection();
        if (c == 'Y') {
            loop = false;
        }
    }

    //编写delHouse() 接收输入的id，调用Service 的del方法
    public void delHouse() {
        System.out.println("=============删除房屋信息=============");
        System.out.print("请输入待删除的房屋编号(-1退出):");
        int delId = Utility.readInt();
        if (delId == -1) {
            System.out.println("=============放弃删除房屋信息=============");
            return;
        }
        //注意该方法本身就有循环判断的逻辑，必须输出Y/N
        char choice = Utility.readConfirmSelection();
        if (choice == 'Y') {
            if (houseService.del(delId)) {
                System.out.println("===========删除房屋信息成功==========");
            } else {
                System.out.println("===========房屋编号不存在，删除失败===========");
            }
        } else {
            System.out.println("=============放弃删除房屋信息=============");
        }
    }

    //编写addHouse() 接收输入，创建House对象，调用add方法
    public void addHouse() {
        System.out.println("============添加房屋============");
        System.out.print("姓名: ");
        String name = Utility.readString(8);
        System.out.print("电话: ");
        String phone = Utility.readString(12);
        System.out.print("地址: ");
        String address = Utility.readString(16);
        System.out.print("月租: ");
        int rent = Utility.readInt();
        System.out.print("状态: ");
        String state = Utility.readString(3);
        //创建一个新的House对象,注意id 是系统分配的，用户不能输入 10000
        House newhouse = new House(0, name, phone, address, rent, state);
        if (houseService.add(newhouse)) {
            System.out.println("==============添加房屋成功==============");
        } else {
            System.out.println("==============添加房屋失败==============");
        }

    }

    //编写listHouses()方法显示房屋列表
    public void listHouses() {
        System.out.println("\n==============房屋列表=============");
        System.out.println("编号\t\t房主\t\t电话\t\t地址\t\t月租\t\t状态(未出租/已出租)");
        House[] houses = houseService.list();//得到所有房屋信息
        for (int i = 0; i < houses.length; i++) {//这里有什么问题？
            if (houses[i] == null) {//如果为null，就不要再显示了
                break;
            }
            System.out.println(houses[i]);
        }
        System.out.println("============房屋列表显示完毕=============");
    }

    //显示主菜单
    public void mainMenu() {
        do {
            System.out.println("\n==============房屋出租系统===============");
            System.out.println("\t\t\t1.新 增 房 源");
            System.out.println("\t\t\t2.查 找 房 源");
            System.out.println("\t\t\t3.删 除 房 源 信 息");
            System.out.println("\t\t\t4.修 改 房 源 信 息");
            System.out.println("\t\t\t5.房 屋 列 表");
            System.out.println("\t\t\t6.退      出");
            System.out.print("请输入你的选择(1-6): ");
            //readChar() 功能：读取键盘输入的一个字符
            key = Utility.readChar();
            switch (key) {
                case '1':
                    addHouse();
                    break;
                case '2':
                    findHouse();
                    break;
                case '3':
                    delHouse();
                    break;
                case '4':
                    update();
                    break;
                case '5':
                    listHouses();
                    break;
                case '6':
                    exit();
                    break;
            }
        } while (loop);
    }
}
