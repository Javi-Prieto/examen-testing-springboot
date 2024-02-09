insert into user_entity (account_non_expired,account_non_locked,avatar,created_at,credentials_non_expired,enabled,full_name,last_password_change_at,password,username,id)
        values (true, true, '', CURRENT_DATE, true, true, 'Juan', CURRENT_DATE, '123', 'lacabra', 1);
insert into user_roles (1, 'ROLE_ADMIN')