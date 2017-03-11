package com.example.administrator.lemondaodemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.lemondaodemo.dataModel.FileDao;
import com.example.administrator.lemondaodemo.dataModel.FileModel;
import com.example.administrator.lemondaodemo.dataModel.User;
import com.example.administrator.lemondaodemo.dataModel.UserDao;
import com.example.lemonlibrary.db.BaseDaoFactory;
import com.example.lemonlibrary.db.IBaseDao;
import com.example.lemonlibrary.db.util.PermissionUtils;

import java.util.List;


@SuppressWarnings("unchecked")
public class MainActivity extends AppCompatActivity {

    private IBaseDao iBaseDao;

    String dbFile;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //数据库地址  文件夹
        dbFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myDb";
        //6.0以上动态申请权限
        PermissionUtils.getInstance().requestPermission(this);
    }

    public void click(View view){
        switch (view.getId()){
            //myselft.db数据库 添加用户
            case R.id.add_user:
                //建立数据库和表的链接
                iBaseDao = BaseDaoFactory.getInstance().getSqliteComponent(UserDao.class,User.class,dbFile,"myselft.db");

                //iBaseDao.insert("insert into tb_user(user_name,user_address,user_psw,user_status,user_id,isMe,myAge,myPhone,aShort) values('鬼刀','六极之地','123456',1,'user_90','true',1.234,457864745,1234)");
                //可以使用对象进行封装存入

                //可以使用sql语句进行存储
                Short si = 123;
                User user = new User("鬼刀","六极之地","123456",0,"user_"+i,true,1.234,457864745l,si);
                iBaseDao.insert(user);
                i++;

                break;
            case R.id.add_file:
                //当需要切换表存储的时候，需要重新建立链接
                iBaseDao = BaseDaoFactory.getInstance().getSqliteComponent(FileDao.class,FileModel.class,dbFile,"myselft.db");
                FileModel fileModel = new FileModel();
                fileModel.setFilePath("d/as:/");
                fileModel.setFileName("qq.apk");
                fileModel.setFileId(123);
                iBaseDao.insert(fileModel);
                break;
            case R.id.add_sqlite_file:
                //当需要切换表存储的时候，需要重新建立链接
                iBaseDao = BaseDaoFactory.getInstance().getSqliteComponent(FileDao.class,FileModel.class,dbFile,"mydownfile.db");
                FileModel fileModel1 = new FileModel();
                fileModel1.setFilePath("d/as:/");
                fileModel1.setFileName("qq.apk");
                fileModel1.setFileId(123);
                iBaseDao.insert(fileModel1);
                break;
            //删除用户
            case R.id.delete_user:
                //建立数据库和表的链接
                iBaseDao = BaseDaoFactory.getInstance().getSqliteComponent(UserDao.class,User.class,dbFile,"myselft.db");

                //使用类对象进行封装删除条件
                //User user2 = new User();
                //user2.setUser_id("user_0");
                //由于手机数据库无法存放布尔值，在寻找条件时，必须对布尔值进行赋值。否则默认是false！
                //user2.setMe(true);
                //iBaseDao.delete(user2);

                //直接使用sql语句删除
                //iBaseDao.delete("delete from tb_user where user_id = 'user_0'");

                //或者使用数组封装条件
                iBaseDao.delete("delete from tb_user where user_id = ?",new String[]{"user_0"});
                break;
            //查询用户
            case R.id.query_user:
                //建立数据库和表的链接
                iBaseDao = BaseDaoFactory.getInstance().getSqliteComponent(UserDao.class,User.class,dbFile,"myselft.db");

                User user1 = new User();
                user1.setName("鬼刀");

                //由于手机数据库无法存放布尔值，在寻找条件时，必须对布尔值进行赋值。否则默认是false！
//                user1.setMe(true);

                //也可以直接查询  当前条件
                List<User> users = iBaseDao.query(user1);

                //可使用表字段来规定排序
                //List<User> users = iBaseDao.query(user1,"user_id",0,10);

                //使用字符串qsl查询
                //List<User>users = iBaseDao.query("select * from tb_user where user_name = '鬼刀'",User.class);

                // 可以用数组装条件
//                List<User>users = iBaseDao.query("select * from tb_user where user_name = ?",User.class,new String[]{"鬼刀"});

                for (User user3:users) {
                    Log.i("tag00",user3.toString());
                    Log.i("tag00","================================");
                }
                break;
            //更新用户
            case R.id.update_user:
                //建立数据库和表的链接  若是表没有换，那么就不用建立新的链接
                iBaseDao = BaseDaoFactory.getInstance().getSqliteComponent(UserDao.class,User.class,dbFile,"myselft.db");

                //更新数据  使用对象进行封装 建议使用这种 非常方便，当然有特殊的需求，就是用sql语句拼接
                User newUser = new User();
                newUser.setName("天南之剑");
                User where = new User();
                where.setMe(true);
                where.setUser_id("user_1");
                iBaseDao.update(newUser,where);

                //用sql语句进行更新
                //iBaseDao.update("update tb_user set user_name = '血天使-奥斯丁' where user_name = '天南之剑'");

                //用数组存放条件语句
//                iBaseDao.update("update tb_user  user_name = '天南之剑' where user_name = '血天使-奥斯丁'");

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //最后关闭数据库，避免内存泄露
        iBaseDao.close();
    }
}
