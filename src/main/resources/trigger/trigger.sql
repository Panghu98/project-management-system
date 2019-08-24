create trigger update_user_role after insert on `project_info` for each row 
                          begin
                          declare leader_name VARCHAR(10) character set utf8;
                          set leader_name = NEW.leader;
                          update user set role = '2' where username =  leader_name and role = 1; 
                          end;

