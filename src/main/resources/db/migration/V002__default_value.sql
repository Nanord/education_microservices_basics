INSERT INTO user_manager.user_status (id, "name", "description") VALUES
	 (1, 'CREATED','Создана'),
	 (2, 'ACTIVE','Активна'),
	 (3, 'BLOCKED','Заблокирована'),
	 (4, 'DELETED','Удалена');
	 
---------------------------------------------------------------------------------------------

INSERT INTO user_manager."role"(sysname, name, description) VALUES
('EVERYONE', 'ВСЕ', 'Роль, доступная всем пользователям системы'),
('ADMIN', 'Админ', 'Роль, позволяющая управлять другими пользователями');