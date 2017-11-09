#db文件夹必须放在Mysql bin路径下：source ./db/create_dbenv.sql
#创建一个完整生成管理平台环境的数据库脚本：所有drop脚本、create脚本依次执行
#注意：该脚本将清除当前DB的所有数据(该脚本仅用于建立初始运行环境或测试环境)
create database if not exists cmpdb default charset utf8 collate utf8_general_ci;
USE cmpdb;

source ./db/activiti.mysql.drop.engine.sql
source ./db/activiti.mysql.drop.history.sql
source ./db/activiti.mysql.drop.identity.sql
source ./db/activiti.mysql.create.engine.sql
source ./db/activiti.mysql.create.history.sql
source ./db/activiti.mysql.create.identity.sql