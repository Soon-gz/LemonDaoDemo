package com.example.administrator.lemondaodemo.dataModel;


import com.example.lemonlibrary.db.DefaultBaseDao;

import java.util.List;

/**
 * Created by ShuWen on 2017/2/12.
 */

public class UserDao extends DefaultBaseDao<User> {


//    @Override
//    public Long insert(User entity) {
//        List<User> list=query(new User());
//        User where = null;
//        for (User user:list)
//        {
//            where =new User();
//            where.setUser_id(user.getUser_id());
//            user.setStatus(0);
//            Log.i(TAG,"用户"+user.getName()+"更改为未登录状态");
//            update(user,where);
//        }
//        Log.i(TAG,"用户"+entity.getName()+"登录");
//        entity.setStatus(1);
//        return super.insert(entity);
//    }


    /**
     * 得到当前登录的User
     * @return
     */
    public User getCurrentUser() {
        User user=new User();
        user.setStatus(1);
        List<User> list=query(user);
        if(list.size()>0)
        {
            return list.get(0);
        }
        return null;
    }
}
