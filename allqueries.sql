create table if not exists Classes (
	id serial PRIMARY KEY NOT NULL,
	class_id INT,
	user_id INT
);
insert into Classes (class_id, user_id) values (4, 11);
insert into Classes (class_id, user_id) values (4, 9);
insert into Classes (class_id, user_id) values (3, 1);
insert into Classes (class_id, user_id) values (2, 2);


create table if not exists Level_of_exp (
	id serial PRIMARY KEY NOT NULL,
	name VARCHAR(20),
	max_value INT
);
insert into Level_of_exp (name, max_value) values ('easy', 30);
insert into Level_of_exp (name, max_value) values ('normal', 60);
insert into Level_of_exp (name, max_value) values ('hard', 90);


create table if not exists AdminPersonals (
	user_id INT,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	phone_number TEXT,
	email TEXT,
	address TEXT
);
insert into AdminPersonals (user_id, first_name, last_name, phone_number, email, address) values (1, 'Giffy', 'De Goey', '171-645-4678', 'gdegoey0@wired.com', '7 Farwell Pass');
insert into AdminPersonals (user_id, first_name, last_name, phone_number, email, address) values (2, 'Reece', 'Thwaite', '277-903-2912', 'rthwaite1@cisco.com', '073 Kinsman Hill');


create table if not exists MentorsPersonals (
	user_id INT,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	phone_number TEXT,
	email TEXT,
	address TEXT
);
insert into MentorsPersonals (user_id, first_name, last_name, phone_number, email, address) values (3, 'Nataniel', 'Rignold', '252-971-9683', 'nrignold0@forbes.com', '99672 American Ash Way');
insert into MentorsPersonals (user_id, first_name, last_name, phone_number, email, address) values (4, 'Aristotle', 'Daen', '699-619-9902', 'adaen1@cbsnews.com', '4863 Wayridge Drive');
insert into MentorsPersonals (user_id, first_name, last_name, phone_number, email, address) values (5, 'Jorey', 'Ummfrey', '610-124-2953', 'jummfrey2@sitemeter.com', '940 Gale Center');
insert into MentorsPersonals (user_id, first_name, last_name, phone_number, email, address) values (6, 'Seline', 'MacGowan', '965-862-6630', 'smacgowan3@phpbb.com', '7 Old Shore Place');
insert into MentorsPersonals (user_id, first_name, last_name, phone_number, email, address) values (7, 'Blithe', 'Verrillo', '615-858-8857', 'bverrillo4@samsung.com', '760 Coolidge Park');
insert into MentorsPersonals (user_id, first_name, last_name, phone_number, email, address) values (8, 'Osgood', 'Faustian', '530-647-7713', 'ofaustian5@eepurl.com', '6578 Westport Drive');
insert into MentorsPersonals (user_id, first_name, last_name, phone_number, email, address) values (9, 'Lorenzo', 'Lease', '359-635-1230', 'llease6@trellian.com', '95 Banding Parkway');
insert into MentorsPersonals (user_id, first_name, last_name, phone_number, email, address) values (10, 'Ola', 'Chastelain', '242-905-7983', 'ochastelain7@biblegateway.com', '184 Birchwood Plaza');
insert into MentorsPersonals (user_id, first_name, last_name, phone_number, email, address) values (11, 'Rosco', 'Hould', '795-807-9879', 'rhould8@constantcontact.com', '766 Forest Point');
insert into MentorsPersonals (user_id, first_name, last_name, phone_number, email, address) values (12, 'Early', 'Mote', '723-335-2935', 'emote9@bigcartel.com', '272 Jana Hill');
insert into MentorsPersonals (user_id, first_name, last_name, phone_number, email, address) values (13, 'Derril', 'Metrick', '265-725-1305', 'dmetricka@jimdo.com', '8 Golf Course Circle');
insert into MentorsPersonals (user_id, first_name, last_name, phone_number, email, address) values (14, 'Deva', 'Belliss', '964-191-7255', 'dbellissb@deliciousdays.com', '88882 Talmadge Court');
insert into MentorsPersonals (user_id, first_name, last_name, phone_number, email, address) values (15, 'Griffith', 'Rollin', '664-114-2196', 'grollinc@cbslocal.com', '998 Comanche Junction');
insert into MentorsPersonals (user_id, first_name, last_name, phone_number, email, address) values (16, 'Irwin', 'Wodeland', '675-338-8954', 'iwodelandd@aboutads.info', '43682 Monument Junction');
insert into MentorsPersonals (user_id, first_name, last_name, phone_number, email, address) values (17, 'Herman', 'Wabb', '571-947-0409', 'hwabbe@sina.com.cn', '1123 Homewood Junction');

create table if not exists Artifacts (
	id serial PRIMARY KEY NOT NULL,
	artifact_name VARCHAR(50),
	artifact_category VARCHAR(50),
	artifact_description TEXT,
	artifact_price INT,
	artifact_availability VARCHAR(50)
);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Combat training', 'Basic', 'Private mentoring', 136, false);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Sanctuary', 'Basic', 'You can spend a day in home office', 74, true);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Time Travel', 'Basic', 'extend SI week assignment deadline by one day', 223, false);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Circle of Sorcery', 'Magic', '60 min workshop by a mentor(s) of the chosen topic', 290, true);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Summon Code Elemental', 'Magic', 'Mentor joins a students team for a one hour', 190, true);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Tome of knowledge', 'Magic', 'Extra material for the current topic', 137, false);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Transform mentors', 'Magic', 'All mentors should dress up as pirates (or just funny) for the day ', 118, true);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Teleport', 'Magic', 'The whole course goes to an off-school program instead for a day', 221, false);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Magic', 'Tipping the fanfare-guy', 'The student can use jukebox exclusively for half day', 253, true);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Crown of prestige', 'Magic', 'You can wear the Crown of prestige for a whole day and thus everyone in your room should call you by a title of your choice (first student who pays on that day)', 249, true);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Royal game session', 'Magic', 'You can choose from a huge list of boardgames to rent for a game afternoon (Arcadia Quest, Arcadia Quest Inferno - KS exclusive edition, Rising Sun - KS exclusive edition, Mechs vs Minions, Dead of Winter - Hungarian edition, Captain Sonar, Hero Realms, Clank in Space, Sheriff of Nottingham etc.)', 95, true);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Circle of Sorcery', 'Magic', '60 min workshop by a mentor(s) of the chosen topic (mentors will organize it within a month)', 129, false);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Myobloc', 'Zathin', 'ES', 215, false);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Argentum Nitricum', 'Cookley', 'Jetta', 275, true);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Foaming Hand Wash', 'Subin', 'Econoline E250', 123, true);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ('Myobloc', 'Zathin', 'ES', 215, false);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ( 'Argentum Nitricum', 'Cookley', 'Jetta', 275, true);
insert into Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability) values ( 'Foaming Hand Wash', 'Subin', 'Econoline E250', 123, true);


create table if not exists Quests (
	id serial PRIMARY KEY NOT NULL,
	quest_name VARCHAR(50),
	quest_category VARCHAR(50),
	quest_description TEXT,
	quest_award INT
);
insert into Quests (quest_name, quest_category, quest_description, quest_award) values ('Exploring a dungeon', 'Basic', 'Finishing a Teamwork week', 70);
insert into Quests (quest_name, quest_category, quest_description, quest_award) values ('Solving the magic puzzle', 'Basic', 'Finishing an SI assignment', 60);
insert into Quests (quest_name, quest_category, quest_description, quest_award) values ('Spot trap', 'Extra', 'Spot a major mistake in the assignment', 50);
insert into Quests (quest_name, quest_category, quest_description, quest_award) values ('Taming a pet', 'Extra', 'Doing a demo about a pet project', 80);
insert into Quests (quest_name, quest_category, quest_description, quest_award) values ('Recruiting some n00bs', 'Extra', 'Taking part in the student screening process', 50);
insert into Quests (quest_name, quest_category, quest_description, quest_award) values ('Forging weapons', 'Extra', 'Organizing a workshop for other students', 10);
insert into Quests (quest_name, quest_category, quest_description, quest_award) values ('Master the mornings', 'Extra', 'Attend 1 months without being late', 70);
insert into Quests (quest_name, quest_category, quest_description, quest_award) values ('Fast as un unicorn', 'Extra', 'Deliver 4 consecutive SI week assignments on time', 30);
insert into Quests (quest_name, quest_category, quest_description, quest_award) values ('Achiever', 'Extra', 'Set up a SMART goal accepted by a mentor, then achieve it', 30);
insert into Quests (quest_name, quest_category, quest_description, quest_award) values ('Fortune', 'Extra', 'Students choose the best project of the week. Selected team scores', 50);
insert into Quests (quest_name, quest_category, quest_description, quest_award) values ('Creating an enchanted scroll', 'Extra', 'Creating extra material for the current TW/SI topic (should be revised by mentors)', 70);
insert into Quests (quest_name, quest_category, quest_description, quest_award) values ('Enter the arena', 'Extra', 'Do a presentation on a meet-up', 80);
insert into Quests (quest_name, quest_category, quest_description, quest_award) values ('Slaying a dragon', 'Basic', 'Passing a Checkpoint', 30);
insert into Quests (quest_name, quest_category, quest_description, quest_award) values ('Visiting the wiseman', 'Extra', 'Going to a company interview', 10);
insert into Quests (quest_name, quest_category, quest_description, quest_award) values ('Treasure hunt!', 'Extra', 'Solving the riddle announced random times in the given time frame', 20);


create table if not exists StudentPersonals (
	user_id INT,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	phone_number TEXT,
	email TEXT,
	address TEXT,
	class_id INT,
	experience_points INT,
	coolcoins INT
);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (18, 'Jobie', 'Yegorovnin', '651-286-8635', 'jyegorovnin0@cmu.edu', '2156 Havey Center', 1, 0, 0);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (19, 'Katleen', 'Top', '418-712-7395', 'ktop1@bloglines.com', '74589 Aberg Pass', 1, 50, 50);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (20, 'Elliot', 'O''Cahsedy', '834-924-4987', 'eocahsedy2@photobucket.com', '70 Farmco Plaza', 1, 100, 20);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (21, 'Ariella', 'Woodstock', '434-612-4875', 'awoodstock3@columbia.edu', '5 Continental Junction', 1, 200, 120);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (22, 'Rosco', 'Prendeville', '819-641-8824', 'rprendeville4@examiner.com', '29495 Donald Road', 1, 60, 50);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (23, 'Bradford', 'Clapton', '957-714-2073', 'bclapton5@cornell.edu', '71528 Mariners Cove Parkway', 1, 70, 20);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (24, 'Deeyn', 'Golsworthy', '394-354-5375', 'dgolsworthy6@t-online.de', '0954 Westport Court', 1, 30, 30);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (25, 'Chariot', 'Bonniface', '504-548-8290', 'cbonniface7@imdb.com', '9 Ilene Avenue', 1, 10, 10);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (26, 'Krista', 'Scammell', '763-803-8534', 'kscammell8@cbc.ca', '2982 Sommers Way', 1, 20, 10);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (27, 'Mort', 'Shurman', '951-706-9791', 'mshurman9@reverbnation.com', '1601 Talisman Pass', 2, 30, 10);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (28, 'Brose', 'Bawden', '994-714-0174', 'bbawdena@time.com', '85 Ilene Pass', 2, 20, 20);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (29, 'Ewan', 'Compford', '333-109-7908', 'ecompfordb@webs.com', '26 Bayside Court', 2, 280, 60);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (30, 'Emera', 'Fagence', '867-936-0441', 'efagencec@opera.com', '515 Thompson Park', 2, 100, 90);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (31, 'Jobina', 'Lindemann', '823-929-9152', 'jlindemannd@techcrunch.com', '5 Sauthoff Crossing', 3, 230, 170);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (32, 'Brnaby', 'Oldland', '177-399-0453', 'boldlande@amazon.co.jp', '6092 Green Ridge Point', 3, 320, 50);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (33, 'Stephani', 'Ousbie', '329-307-7611', 'sousbief@dmoz.org', '73031 Drewry Alley', 3, 120, 30);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (34, 'Cory', 'Gunter', '182-339-4679', 'cgunterg@ask.com', '211 East Drive', 3, 100, 100);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (35, 'Meridith', 'Treweke', '247-980-0278', 'mtrewekeh@over-blog.com', '98 Corben Plaza', 3, 100, 20);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (36, 'Andeee', 'Cottel', '723-435-2090', 'acotteli@umich.edu', '49 Cody Parkway', 4, 220, 220);
insert into StudentPersonals (user_id, first_name, last_name, phone_number, email, address, experience_points, class_id, coolcoins) values (37, 'Johan', 'de Broke', '747-963-4995', 'jdebrokej@delicious.com', '02041 Pearson Alley', 4, 180, 0);


create table if not exists Users (
	id serial PRIMARY KEY NOT NULL,
	login VARCHAR(50),
	password VARCHAR(50),
	usertype VARCHAR(50)
);
insert into Users (login, password, usertype) values ('kdavid0', 'FhNbwi4mq', 'admin');
insert into Users (login, password, usertype) values ('aaykroyd1', '7zuouW89', 'admin');
insert into Users (login, password, usertype) values ('lclinch2', 'WhShzKui6r3', 'mentor');
insert into Users (login, password, usertype) values ('cshere3', 'QWY2y6', 'mentor');
insert into Users (login, password, usertype) values ('kwitchard4', 'PJtYV4H1', 'mentor');
insert into Users (login, password, usertype) values ('tdifilippo5', 'ekUYcLgl4', 'mentor');
insert into Users (login, password, usertype) values ('aflipek6', '6NcUD0LwYDGA', 'mentor');
insert into Users (login, password, usertype) values ('rmacnish7', 'bmxg6PoEqM', 'mentor');
insert into Users (login, password, usertype) values ('sguppie8', 'GKUk9F', 'mentor');
insert into Users (login, password, usertype) values ('mslimming9', 'Go1zfN8Du4', 'mentor');
insert into Users (login, password, usertype) values ('cendacotta', 'HKyfjOxj', 'mentor');
insert into Users (login, password, usertype) values ('aiskovb', 'xta6tXVNAwD', 'mentor');
insert into Users (login, password, usertype) values ('sseabornec', 'J0mNqg', 'mentor');
insert into Users (login, password, usertype) values ('ascourfieldd', '8lb7DnRJPbG', 'mentor');
insert into Users (login, password, usertype) values ('afairleme', 'dvclZVAnbE5', 'mentor');
insert into Users (login, password, usertype) values ('jbowmenf', 'gNph5ydnm3', 'mentor');
insert into Users (login, password, usertype) values ('blintotg', 'iD49MxOUx8a', 'mentor');
insert into Users (login, password, usertype) values ('cdibbeh', 'i18J9YIgYYM', 'student');
insert into Users (login, password, usertype) values ('tryami', 'lkfsKpZoE', 'student');
insert into Users (login, password, usertype) values ('jbucklej', 'CNUAhKX8J', 'student');
insert into Users (login, password, usertype) values ('fhaddockk', 'CCSrbBRMGDw3', 'student');
insert into Users (login, password, usertype) values ('kferonl', 'U6VKnw79i', 'student');
insert into Users (login, password, usertype) values ('msouthworthm', '8cIpXHNy', 'student');
insert into Users (login, password, usertype) values ('tmelledyn', 'oTH50B9I0', 'student');
insert into Users (login, password, usertype) values ('bneggrinio', 'xVAA9u', 'student');
insert into Users (login, password, usertype) values ('kquirkp', 'veku5xLIX', 'student');
insert into Users (login, password, usertype) values ('lcollecottq', '8tQYqMw4z', 'student');
insert into Users (login, password, usertype) values ('hhampr', 'GIfW8bTkS7t', 'student');
insert into Users (login, password, usertype) values ('bwescotts', 'OeEwCtYkM', 'student');
insert into Users (login, password, usertype) values ('tringet', 'XcStl4jt3U', 'student');
insert into Users (login, password, usertype) values ('fgeffcocku', 'JseM2vmPF2', 'student');
insert into Users (login, password, usertype) values ('gmirrallsv', '51vZiDO', 'student');
insert into Users (login, password, usertype) values ('asoftleyw', 'd6sl4sS2q', 'student');
insert into Users (login, password, usertype) values ('elacyx', 'lKgmOOxgapo', 'student');
insert into Users (login, password, usertype) values ('gstoakleyy', 'kyr8kff', 'student');
insert into Users (login, password, usertype) values ('abaccupz', 'CZzwI2wJm3', 'student');
insert into Users (login, password, usertype) values ('cgrewcock10', 'Nz1yWC9YApYL', 'student');


create table if not exists Users_artifacts (
	id serial PRIMARY KEY NOT NULL,
	user_id INT,
	artifact_id INT
);
insert into Users_artifacts (user_id, artifact_id) values (18, 4);
insert into Users_artifacts (user_id, artifact_id) values (19, 10);
insert into Users_artifacts (user_id, artifact_id) values (24, 5);
insert into Users_artifacts (user_id, artifact_id) values (27, 5);
insert into Users_artifacts (user_id, artifact_id) values (28, 3);
insert into Users_artifacts (user_id, artifact_id) values (30, 4);
insert into Users_artifacts (user_id, artifact_id) values (33, 8);
insert into Users_artifacts (user_id, artifact_id) values (33, 4);



create table if not exists Users_quests (

	id serial PRIMARY KEY NOT NULL,
	user_id INT,
	quest_id INT
);
insert into Users_quests (user_id, quest_id) values (18, 7);
insert into Users_quests (user_id, quest_id) values (25, 8);
insert into Users_quests (user_id, quest_id) values (36, 3);
insert into Users_quests (user_id, quest_id) values (22, 10);
insert into Users_quests (user_id, quest_id) values (34, 2);
insert into Users_quests (user_id, quest_id) values (37, 9);
insert into Users_quests (user_id, quest_id) values (30, 6);