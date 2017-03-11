## [我的博客](http://blog.csdn.net/sw5131899/article/details/57415572)

	
	数据库在某些特定需求下是很重要的，像持久化数据，一些不需要实时或者长时间不变的数据，
可以放在数据库中做缓存，这样就算用户网络不好或者断网的情况下，依然是可以查看一些以前的数据。像新闻或者咨询类。
这样做是不可避免的。提高用户的体验。谷歌为我们开发者提供了最基础的数据库操作类，
同时也提供了SqliteDatabase直接创建修改数据库和表。但是只是这样不能满足我们平时的敏捷开发。
作为一个应用层程序员，很多时候只是想去关心存储和读取数据，数据处理等问题的解决。
而不是花时间和精力在如何去存，如何去取数据库数据。这就是架构出现的前提。
将一些面向过程的算法或者业务逻辑封装，可再次调用。易扩展、高灵敏。让程序猿专心的去整理项目需求和逻辑。
解放大脑，提高工作效率。

现在数据库有很多三方框架，比如GreenDao,Afinal、xUtils、ThinkAndroid、volley等等。但是这些三方导入后，文件数太大了。而且很多都集成了网络加载框架，图片加载框架。这样就有了一定的耦合性。比如我只喜欢xUtils的数据库框架，不喜欢它的网络加载，想用Volley的网络加载框架。咋办呢？两个都导入？是的，可以这样，但是项目的文件数就太大了。编译时压力大。而且一些公司想让程序猿写自己的框架，不使用别人发布的框架，这样代码是可以高扩展的。出现什么问题也是可以及时修改。如果导入的三方库在项目成熟阶段出现问题。那是不是要推翻这个库，重新去导入一个，重新在写一遍逻辑，而且又有谁能保证，再次导入的库不会出错呢？

我把大致的封装思路，以图片的形式展示出来。

![](https://github.com/SingleShu/LemonDaoDemo/raw/master/Logo/a.png)  

对应的UML逻辑图

![](https://github.com/SingleShu/LemonDaoDemo/raw/master/Logo/b.png) 

使用起来也非常方便，这里的映射逻辑和GreenDao的底层源码使用的是一个原理。采用表和类属性映射，并对其进行了缓存。只有在切换表的链接时，会重新建立链接。

 优点：

1、支持sql语句自拼接。数据库直接执行sql语句。

2、支持传入类对象进行增删改查，比如User，在insert的时候，插入User的实例，属性都封装在user中。通过反射获取它的属性值，存入数据库。

3、高扩展，每个新建的存储类型。直接继承DefaultBaseDao即可。可以在这里做一些该类独有的操作。

4、支持多数据库切换，多数据库操作。同时支持数据库分库管理，多数据库多版本多用户同时升级，采取XML脚本升级。这里的脚本语句没有封装。

5、支持boolean、byte、short、int、long、float、double数据类型的存储和读取。

 

使用注意事项：

1、创建存储数据类时，需要使用注解去标注类名（表名@DbTable）、类属性（表字段@DbField）。若是有主键（@DbPrimaryField）也需要标注。

2、需要创建一个相应的Dao层类和数据库的链接。


使用时导入gandle： compile 'com.singleshu8:LemonDao:1.0.4'

创建一个Dao层，这里可以操作数据库，像多数据升级时，这里可以做一些特定处理。对外扩展
```Java
public class FileDao extends DefaultBaseDao {
}
```
```Java
@DbTable(value = "tb_file")
public class FileModel {

    @DbPrimaryField(value = "id")
    private int id;
    @DbField(value = "tb_filename")
    private String fileName;
    @DbField(value = "tb_filepath")
    private String filePath;
    @DbField(value = "tb_fileid")
    private int fileId;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
```
```Java
@DbTable(value = "tb_user")
public class User {

    @DbPrimaryField(value = "id")
    private Integer id;
    @DbField(value = "user_name")
    private String name;
    @DbField(value = "user_address")
    private String address;
    @DbField(value = "user_psw")
    private String psw;
    @DbField(value = "user_status")
    private Integer status;
    @DbField(value = "user_id")
    private String user_id;
    @DbField(value = "isMe")
    private Boolean isMe;
    @DbField(value = "myAge")
    private Double myAge;
    @DbField(value = "myPhone")
    private Long myPhone;
    @DbField(value = "aShort")
    private Short aShort;

    public User(String name, String address, String psw, Integer status, String user_id, Boolean isMe, Double myAge, Long myPhone, Short aShort) {
        this.name = name;
        this.address = address;
        this.psw = psw;
        this.status = status;
        this.user_id = user_id;
        this.isMe = isMe;
        this.myAge = myAge;
        this.myPhone = myPhone;
        this.aShort = aShort;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Boolean getMe() {
        return isMe;
    }

    public void setMe(Boolean me) {
        isMe = me;
    }

    public Double getMyAge() {
        return myAge;
    }

    public void setMyAge(Double myAge) {
        this.myAge = myAge;
    }

    public Long getMyPhone() {
        return myPhone;
    }

    public void setMyPhone(Long myPhone) {
        this.myPhone = myPhone;
    }

    public Short getaShort() {
        return aShort;
    }

    public void setaShort(Short aShort) {
        this.aShort = aShort;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", psw='" + psw + '\'' +
                ", status=" + status +
                ", user_id='" + user_id + '\'' +
                ", isMe=" + isMe +
                ", myAge=" + myAge +
                ", myPhone=" + myPhone +
                ", aShort=" + aShort +
                '}';
    }
}
```
```Java
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
```
具体使用
```Java
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
```
有关于数据库分库和多版本多用户脚本升级，这个在小项目用的很少。小项目一般是几张表就搞定了。查询是加个userId，但是当
数据量过大时，这样做会很难受。数据库升级在表少的时候，可以直接拼接字符串。但是当表多了之后，这样做无疑是不正确的。
若是对数据库分库和多版本多用户升级感兴趣的朋友。可以联系我，794918578@qq.com；

#觉得有用的朋友请给个star，谢谢




