package com.zhao.ChargeUp.unit;

import android.widget.Toast;

import com.zhao.ChargeUp.MainFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zhao on 2016/3/27.
 * 用户
 */
public class User {
    private static LinkedList<User> users = new LinkedList<User>();
    private static User currentUser;
    private String name;
    //用户的所有收支记录
    private List<Record> records;
    //用户的金额总量
    private double amount;

    /**
     * 创建用户
     * @param name 用户名
     * @param amount 初始资金
     */
    public User(String name, double amount) {
        this.name = name;
        this.amount = amount;
        this.records = new LinkedList<Record>();
        User.users.add(this);
        //将新建的User自动设为当前用户
        User.currentUser=this;
    }

    public List<Record> getRecords() {
        return records;
    }

    /**
     * 添加一条记录
     * @param record 收支记录
     */
    public void addRecord(Record record){
        //支出情况在总金额减去支出 收入情况下在总金额中加上收入
        if (record.getRecordType() == Record.EXPEND) {
            amount -= record.getSum();
        } else {
            amount+=record.getSum();
        }
        records.add(0,record);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public static LinkedList<User> getUsers() {
        return users;
    }

    public void setCurrentUser() {
        User.currentUser = this;
    }

    public static User getCurrentUser() {
        return User.currentUser;
    }
    //是否为当前用户
    public boolean isCurrentUser() {
        if (User.getCurrentUser().equals(this))
            return true;
        return false;
    }

    public String getName() {
        return name;
    }

    /**
     * 删除一条收支记录
     * @param position 删除的位置
     * @return 如果删除成功返回true 失败返回false
     */
    public boolean removeRecord(int position) {
        if (position >= records.size()) {
            return false;
        }
        Record removedRecord = records.remove(position);
        if (removedRecord.getRecordType() == Record.EXPEND) {
            this.amount+=removedRecord.getSum();
        }else{
            this.amount-=removedRecord.getSum();
        }
        return true;
    }

    /**
     * 删除User
     * @param position 在Users中的位置
     * @return 成功返回true 失败返回false
     */
    public static boolean removeUser(int position) {
        if (position >= User.users.size()) {
            return false;
        }

        if (User.users.size() == 0) {
            return false;
        }

        User removedUser = User.users.remove(position);
        //如果被删除用户为当前用户则将第一个用户设置为当前用户
        if (removedUser.isCurrentUser()) {
            User.users.get(0).setCurrentUser();
        }
        return true;
    }
}