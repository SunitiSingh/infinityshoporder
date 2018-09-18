-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Sep 19, 2018 at 12:20 AM
-- Server version: 5.5.42
-- PHP Version: 5.6.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `infinityshopord`
--
CREATE DATABASE IF NOT EXISTS `infinityshopord` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `infinityshopord`;

-- --------------------------------------------------------

--
-- Table structure for table `commerce_billing_address`
--

DROP TABLE IF EXISTS `commerce_billing_address`;
CREATE TABLE `commerce_billing_address` (
  `id` bigint(20) NOT NULL,
  `address_1` varchar(255) DEFAULT NULL,
  `address_2` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `postalcode` varchar(255) DEFAULT NULL,
  `create_date` datetime,
  `update_date` datetime
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `commerce_e_pay`
--

DROP TABLE IF EXISTS `commerce_e_pay`;
CREATE TABLE `commerce_e_pay` (
  `id` bigint(20) NOT NULL,
  `e_pay_type` varchar(255) DEFAULT NULL,
  `e_pay_token` varchar(255) DEFAULT NULL,
  `create_date` datetime,
  `update_date` datetime
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `commerce_item`
--

DROP TABLE IF EXISTS `commerce_item`;
CREATE TABLE `commerce_item` (
  `id` bigint(20) NOT NULL,
  `skuid` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `creation_date` datetime NOT NULL,
  `update_date` datetime,
  `commerce_order_id` bigint(20) DEFAULT NULL,
  `price_id` bigint(20) DEFAULT NULL,
  `ship_info_id` bigint(20) DEFAULT NULL,
  `pay_info_id` bigint(20) DEFAULT NULL,
  `idx` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `commerce_item_payment`
--

DROP TABLE IF EXISTS `commerce_item_payment`;
CREATE TABLE `commerce_item_payment` (
  `payments_id` bigint(20) NOT NULL,
  `commerce_items_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `commerce_item_pay_info`
--

DROP TABLE IF EXISTS `commerce_item_pay_info`;
CREATE TABLE `commerce_item_pay_info` (
  `id` bigint(20) NOT NULL,
  `status` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `order_payment_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `commerce_item_price`
--

DROP TABLE IF EXISTS `commerce_item_price`;
CREATE TABLE `commerce_item_price` (
  `id` bigint(20) NOT NULL,
  `price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `commerce_item_price_ng`
--

DROP TABLE IF EXISTS `commerce_item_price_ng`;
CREATE TABLE `commerce_item_price_ng` (
  `id` bigint(20) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `ngid` varchar(255) DEFAULT NULL,
  `commerce_item_price_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `commerce_item_shipcontainer`
--

DROP TABLE IF EXISTS `commerce_item_shipcontainer`;
CREATE TABLE `commerce_item_shipcontainer` (
  `shipcontainers_id` bigint(20) NOT NULL,
  `commerce_items_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `commerce_item_ship_info`
--

DROP TABLE IF EXISTS `commerce_item_ship_info`;
CREATE TABLE `commerce_item_ship_info` (
  `id` bigint(20) NOT NULL,
  `status` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `ship_container_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `commerce_order`
--

DROP TABLE IF EXISTS `commerce_order`;
CREATE TABLE `commerce_order` (
  `id` bigint(20) NOT NULL,
  `status` varchar(255) NOT NULL,
  `custid` varchar(255) NOT NULL,
  `creation_date` datetime NOT NULL,
  `placed_date` datetime,
  `update_date` datetime
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1 COMMENT='The CommerceOrder entity.';

--
-- Dumping data for table `commerce_order`
--

INSERT INTO `commerce_order` (`id`, `status`, `custid`, `creation_date`, `placed_date`, `update_date`) VALUES
(4, 'CART', '552', '2018-09-15 22:30:34', NULL, NULL),
(6, 'CART', '337', '2018-09-16 14:29:26', NULL, NULL),
(7, 'CART', '554', '2018-09-17 10:59:00', NULL, '2018-09-17 10:59:14'),
(8, 'CART', '665', '2018-09-17 11:00:00', NULL, '2018-09-17 11:00:40'),
(9, 'CART', '983', '2018-09-17 11:04:00', NULL, '2018-09-17 11:04:09'),
(10, 'CART', '879', '2018-09-17 11:08:32', NULL, NULL),
(11, 'CART', '787', '2018-09-17 11:10:00', '1969-12-31 16:00:00', '2018-09-17 12:23:07'),
(12, 'CART', '334', '2018-09-17 13:17:25', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `commerce_order_payment`
--

DROP TABLE IF EXISTS `commerce_order_payment`;
CREATE TABLE `commerce_order_payment` (
  `id` bigint(20) NOT NULL,
  `paystatus` varchar(255) DEFAULT NULL,
  `payment_amount` decimal(10,2) DEFAULT NULL,
  `payment_type` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `billing_phone` varchar(255) DEFAULT NULL,
  `commerce_order_id` bigint(20) DEFAULT NULL,
  `card_id` bigint(20) DEFAULT NULL,
  `epay_id` bigint(20) DEFAULT NULL,
  `billing_address_id` bigint(20) DEFAULT NULL,
  `idx` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `commerce_order_payment`
--

INSERT INTO `commerce_order_payment` (`id`, `paystatus`, `payment_amount`, `payment_type`, `first_name`, `last_name`, `billing_phone`, `commerce_order_id`, `card_id`, `epay_id`, `billing_address_id`, `idx`) VALUES
(1, 'INITIAL', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0),
(2, 'INITIAL', NULL, NULL, NULL, NULL, NULL, 4, NULL, NULL, NULL, 0),
(3, 'INITIAL', NULL, NULL, NULL, NULL, NULL, 6, NULL, NULL, NULL, 0),
(5, 'INITIAL', NULL, NULL, NULL, NULL, NULL, 10, NULL, NULL, NULL, 0),
(6, 'INITIAL', NULL, NULL, NULL, NULL, NULL, 11, NULL, NULL, NULL, 0),
(7, 'INITIAL', NULL, NULL, NULL, NULL, NULL, 12, NULL, NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `commerce_order_price`
--

DROP TABLE IF EXISTS `commerce_order_price`;
CREATE TABLE `commerce_order_price` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `commerce_order_id` bigint(20) DEFAULT NULL,
  `idx` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `commerce_order_price_ng`
--

DROP TABLE IF EXISTS `commerce_order_price_ng`;
CREATE TABLE `commerce_order_price_ng` (
  `id` bigint(20) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `ngid` varchar(255) DEFAULT NULL,
  `commerce_order_price_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `commerce_payment_card`
--

DROP TABLE IF EXISTS `commerce_payment_card`;
CREATE TABLE `commerce_payment_card` (
  `id` bigint(20) NOT NULL,
  `card_type` varchar(255) DEFAULT NULL,
  `card_number` varchar(255) DEFAULT NULL,
  `exp_month` int(11) DEFAULT NULL,
  `exp_year` int(11) DEFAULT NULL,
  `create_date` datetime,
  `update_date` datetime
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `commerce_shipping_container`
--

DROP TABLE IF EXISTS `commerce_shipping_container`;
CREATE TABLE `commerce_shipping_container` (
  `id` bigint(20) NOT NULL,
  `shipstatus` varchar(255) NOT NULL,
  `carrier` varchar(255) DEFAULT NULL,
  `creation_date` datetime NOT NULL,
  `update_date` datetime,
  `commerce_order_id` bigint(20) DEFAULT NULL,
  `price_id` bigint(20) DEFAULT NULL,
  `idx` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `commerce_shipping_container`
--

INSERT INTO `commerce_shipping_container` (`id`, `shipstatus`, `carrier`, `creation_date`, `update_date`, `commerce_order_id`, `price_id`, `idx`) VALUES
(1, 'INIT', NULL, '2018-09-15 22:17:56', NULL, NULL, NULL, 0),
(2, 'INIT', NULL, '2018-09-15 22:30:34', NULL, 4, NULL, 0),
(3, 'INIT', NULL, '2018-09-16 14:29:26', NULL, 6, NULL, 0),
(5, 'INIT', NULL, '2018-09-17 11:08:32', NULL, 10, NULL, 0),
(6, 'INIT', NULL, '2018-09-17 11:10:56', NULL, 11, NULL, 0),
(7, 'INIT', NULL, '2018-09-17 13:17:25', NULL, 12, NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `commerce_ship_container_price`
--

DROP TABLE IF EXISTS `commerce_ship_container_price`;
CREATE TABLE `commerce_ship_container_price` (
  `id` bigint(20) NOT NULL,
  `price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `commerce_ship_price_ng`
--

DROP TABLE IF EXISTS `commerce_ship_price_ng`;
CREATE TABLE `commerce_ship_price_ng` (
  `id` bigint(20) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `commerce_ship_container_price_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `DATABASECHANGELOG`
--

DROP TABLE IF EXISTS `DATABASECHANGELOG`;
CREATE TABLE `DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `DATABASECHANGELOG`
--

INSERT INTO `DATABASECHANGELOG` (`ID`, `AUTHOR`, `FILENAME`, `DATEEXECUTED`, `ORDEREXECUTED`, `EXECTYPE`, `MD5SUM`, `DESCRIPTION`, `COMMENTS`, `TAG`, `LIQUIBASE`, `CONTEXTS`, `LABELS`, `DEPLOYMENT_ID`) VALUES
('00000000000001', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2018-09-14 13:26:06', 1, 'EXECUTED', '7:9d88ecd533d5a3530e304f778b9dc982', 'createTable tableName=jhi_persistent_audit_event; createTable tableName=jhi_persistent_audit_evt_data; addPrimaryKey tableName=jhi_persistent_audit_evt_data; createIndex indexName=idx_persistent_audit_event, tableName=jhi_persistent_audit_event; c...', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024059-1', 'jhipster', 'config/liquibase/changelog/20180913024059_added_entity_CommerceOrder.xml', '2018-09-14 13:26:06', 2, 'EXECUTED', '7:0a5da9258b783f07c56b11539f067c13', 'createTable tableName=commerce_order; dropDefaultValue columnName=creation_date, tableName=commerce_order; dropDefaultValue columnName=placed_date, tableName=commerce_order; dropDefaultValue columnName=update_date, tableName=commerce_order', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024100-1', 'jhipster', 'config/liquibase/changelog/20180913024100_added_entity_CommerceOrderPrice.xml', '2018-09-14 13:26:06', 3, 'EXECUTED', '7:d858ed840dec717c3bd94334817afc91', 'createTable tableName=commerce_order_price', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024101-1', 'jhipster', 'config/liquibase/changelog/20180913024101_added_entity_CommerceOrderPriceNg.xml', '2018-09-14 13:26:06', 4, 'EXECUTED', '7:6149674a976cfd8eac226add2ce71f79', 'createTable tableName=commerce_order_price_ng', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024102-1', 'jhipster', 'config/liquibase/changelog/20180913024102_added_entity_CommerceItem.xml', '2018-09-14 13:26:06', 5, 'EXECUTED', '7:9a4611d7c97d0e1db7f0cdfccc904c52', 'createTable tableName=commerce_item; dropDefaultValue columnName=creation_date, tableName=commerce_item; dropDefaultValue columnName=update_date, tableName=commerce_item; createTable tableName=commerce_item_shipcontainer; addPrimaryKey tableName=c...', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024103-1', 'jhipster', 'config/liquibase/changelog/20180913024103_added_entity_CommerceItemPrice.xml', '2018-09-14 13:26:06', 6, 'EXECUTED', '7:add0f851a403341c918c40038b7eb1e4', 'createTable tableName=commerce_item_price', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024104-1', 'jhipster', 'config/liquibase/changelog/20180913024104_added_entity_CommerceItemPriceNg.xml', '2018-09-14 13:26:06', 7, 'EXECUTED', '7:6ac4d2f6efbafad5cac0be395ef1d017', 'createTable tableName=commerce_item_price_ng', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024105-1', 'jhipster', 'config/liquibase/changelog/20180913024105_added_entity_CommerceShippingContainer.xml', '2018-09-14 13:26:06', 8, 'EXECUTED', '7:8e50bcee1f50bbe9f0b759f0f12be12b', 'createTable tableName=commerce_shipping_container; dropDefaultValue columnName=creation_date, tableName=commerce_shipping_container; dropDefaultValue columnName=update_date, tableName=commerce_shipping_container', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024106-1', 'jhipster', 'config/liquibase/changelog/20180913024106_added_entity_CommerceShipContainerPrice.xml', '2018-09-14 13:26:06', 9, 'EXECUTED', '7:a8c25bf0040333814dbb786cc08aa01a', 'createTable tableName=commerce_ship_container_price', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024107-1', 'jhipster', 'config/liquibase/changelog/20180913024107_added_entity_CommerceShipPriceNg.xml', '2018-09-14 13:26:06', 10, 'EXECUTED', '7:77055aa5e2716f1cc983bc3d9f4d15e8', 'createTable tableName=commerce_ship_price_ng', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024108-1', 'jhipster', 'config/liquibase/changelog/20180913024108_added_entity_CommerceOrderPayment.xml', '2018-09-14 13:26:06', 11, 'EXECUTED', '7:bb318473ebdb2947a58e742f16910394', 'createTable tableName=commerce_order_payment', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024109-1', 'jhipster', 'config/liquibase/changelog/20180913024109_added_entity_CommercePaymentCard.xml', '2018-09-14 13:26:06', 12, 'EXECUTED', '7:bcdd8e5b82325ddab276c983811c12e3', 'createTable tableName=commerce_payment_card; dropDefaultValue columnName=create_date, tableName=commerce_payment_card; dropDefaultValue columnName=update_date, tableName=commerce_payment_card', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024110-1', 'jhipster', 'config/liquibase/changelog/20180913024110_added_entity_CommerceEPay.xml', '2018-09-14 13:26:06', 13, 'EXECUTED', '7:a364a1be3740c4c69b3a16d218ca525f', 'createTable tableName=commerce_e_pay; dropDefaultValue columnName=create_date, tableName=commerce_e_pay; dropDefaultValue columnName=update_date, tableName=commerce_e_pay', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024111-1', 'jhipster', 'config/liquibase/changelog/20180913024111_added_entity_CommerceBillingAddress.xml', '2018-09-14 13:26:06', 14, 'EXECUTED', '7:ce5bd616881f1c20dbdf505555344090', 'createTable tableName=commerce_billing_address; dropDefaultValue columnName=create_date, tableName=commerce_billing_address; dropDefaultValue columnName=update_date, tableName=commerce_billing_address', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180914202232-1', 'jhipster', 'config/liquibase/changelog/20180914202232_added_entity_CommerceItemShipInfo.xml', '2018-09-14 13:26:06', 15, 'EXECUTED', '7:e9980bece114b1134684933c794701ee', 'createTable tableName=commerce_item_ship_info', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180914202233-1', 'jhipster', 'config/liquibase/changelog/20180914202233_added_entity_CommerceItemPayInfo.xml', '2018-09-14 13:26:06', 16, 'EXECUTED', '7:8c8ed8a7908b6fe3b8af4a9d04e0ce80', 'createTable tableName=commerce_item_pay_info', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024100-2', 'jhipster', 'config/liquibase/changelog/20180913024100_added_entity_constraints_CommerceOrderPrice.xml', '2018-09-14 13:26:06', 17, 'EXECUTED', '7:4efae809cb178a25fe1044e47ff818d7', 'addForeignKeyConstraint baseTableName=commerce_order_price, constraintName=fk_commerce_order_price_commerce_order_id, referencedTableName=commerce_order', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024101-2', 'jhipster', 'config/liquibase/changelog/20180913024101_added_entity_constraints_CommerceOrderPriceNg.xml', '2018-09-14 13:26:06', 18, 'EXECUTED', '7:fff84ff3b316061a57c076b2b497d760', 'addForeignKeyConstraint baseTableName=commerce_order_price_ng, constraintName=fk_commerce_order_price_ng_commerce_order_price_id, referencedTableName=commerce_order_price', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024102-2', 'jhipster', 'config/liquibase/changelog/20180913024102_added_entity_constraints_CommerceItem.xml', '2018-09-14 13:26:06', 19, 'EXECUTED', '7:9c9cc6409b02fc3877fd0d1f5436997d', 'addForeignKeyConstraint baseTableName=commerce_item, constraintName=fk_commerce_item_commerce_order_id, referencedTableName=commerce_order; addForeignKeyConstraint baseTableName=commerce_item, constraintName=fk_commerce_item_price_id, referencedTa...', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024104-2', 'jhipster', 'config/liquibase/changelog/20180913024104_added_entity_constraints_CommerceItemPriceNg.xml', '2018-09-14 13:26:06', 20, 'EXECUTED', '7:34b39833168be8d2df307f1ca0298520', 'addForeignKeyConstraint baseTableName=commerce_item_price_ng, constraintName=fk_commerce_item_price_ng_commerce_item_price_id, referencedTableName=commerce_item_price', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024105-2', 'jhipster', 'config/liquibase/changelog/20180913024105_added_entity_constraints_CommerceShippingContainer.xml', '2018-09-14 13:26:06', 21, 'EXECUTED', '7:eacf967b76fa848d87220b57be2ef772', 'addForeignKeyConstraint baseTableName=commerce_shipping_container, constraintName=fk_commerce_shipping_container_commerce_order_id, referencedTableName=commerce_order; addForeignKeyConstraint baseTableName=commerce_shipping_container, constraintNa...', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024107-2', 'jhipster', 'config/liquibase/changelog/20180913024107_added_entity_constraints_CommerceShipPriceNg.xml', '2018-09-14 13:26:06', 22, 'EXECUTED', '7:2ba5a96ce4e22dca08c3927f36b73337', 'addForeignKeyConstraint baseTableName=commerce_ship_price_ng, constraintName=fk_commerce_ship_price_ng_commerce_ship_container_price_id, referencedTableName=commerce_ship_container_price', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180913024108-2', 'jhipster', 'config/liquibase/changelog/20180913024108_added_entity_constraints_CommerceOrderPayment.xml', '2018-09-14 13:26:06', 23, 'EXECUTED', '7:627b551acbac0a3e39e299de306d262b', 'addForeignKeyConstraint baseTableName=commerce_order_payment, constraintName=fk_commerce_order_payment_commerce_order_id, referencedTableName=commerce_order; addForeignKeyConstraint baseTableName=commerce_order_payment, constraintName=fk_commerce_...', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180914202232-2', 'jhipster', 'config/liquibase/changelog/20180914202232_added_entity_constraints_CommerceItemShipInfo.xml', '2018-09-14 13:26:06', 24, 'EXECUTED', '7:c484373239d2692549854087b923bb5d', 'addForeignKeyConstraint baseTableName=commerce_item_ship_info, constraintName=fk_commerce_item_ship_info_ship_container_id, referencedTableName=commerce_shipping_container', '', NULL, '3.5.4', NULL, NULL, '6956765953'),
('20180914202233-2', 'jhipster', 'config/liquibase/changelog/20180914202233_added_entity_constraints_CommerceItemPayInfo.xml', '2018-09-14 13:26:06', 25, 'EXECUTED', '7:badd2790b833e49970c7cedd51dc51fd', 'addForeignKeyConstraint baseTableName=commerce_item_pay_info, constraintName=fk_commerce_item_pay_info_order_payment_id, referencedTableName=commerce_order_payment', '', NULL, '3.5.4', NULL, NULL, '6956765953');

-- --------------------------------------------------------

--
-- Table structure for table `DATABASECHANGELOGLOCK`
--

DROP TABLE IF EXISTS `DATABASECHANGELOGLOCK`;
CREATE TABLE `DATABASECHANGELOGLOCK` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `DATABASECHANGELOGLOCK`
--

INSERT INTO `DATABASECHANGELOGLOCK` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
(1, b'0', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `jhi_persistent_audit_event`
--

DROP TABLE IF EXISTS `jhi_persistent_audit_event`;
CREATE TABLE `jhi_persistent_audit_event` (
  `event_id` bigint(20) NOT NULL,
  `principal` varchar(50) NOT NULL,
  `event_date` timestamp NULL DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `jhi_persistent_audit_evt_data`
--

DROP TABLE IF EXISTS `jhi_persistent_audit_evt_data`;
CREATE TABLE `jhi_persistent_audit_evt_data` (
  `event_id` bigint(20) NOT NULL,
  `name` varchar(150) NOT NULL,
  `value` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `commerce_billing_address`
--
ALTER TABLE `commerce_billing_address`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `commerce_e_pay`
--
ALTER TABLE `commerce_e_pay`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `commerce_item`
--
ALTER TABLE `commerce_item`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ux_commerce_item_price_id` (`price_id`),
  ADD UNIQUE KEY `ux_commerce_item_ship_info_id` (`ship_info_id`),
  ADD UNIQUE KEY `ux_commerce_item_pay_info_id` (`pay_info_id`),
  ADD KEY `fk_commerce_item_commerce_order_id` (`commerce_order_id`);

--
-- Indexes for table `commerce_item_payment`
--
ALTER TABLE `commerce_item_payment`
  ADD PRIMARY KEY (`commerce_items_id`,`payments_id`),
  ADD KEY `fk_commerce_item_payment_payments_id` (`payments_id`);

--
-- Indexes for table `commerce_item_pay_info`
--
ALTER TABLE `commerce_item_pay_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ux_commerce_item_pay_info_order_payment_id` (`order_payment_id`);

--
-- Indexes for table `commerce_item_price`
--
ALTER TABLE `commerce_item_price`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `commerce_item_price_ng`
--
ALTER TABLE `commerce_item_price_ng`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_commerce_item_price_ng_commerce_item_price_id` (`commerce_item_price_id`);

--
-- Indexes for table `commerce_item_shipcontainer`
--
ALTER TABLE `commerce_item_shipcontainer`
  ADD PRIMARY KEY (`commerce_items_id`,`shipcontainers_id`),
  ADD KEY `fk_commerce_item_shipcontainer_shipcontainers_id` (`shipcontainers_id`);

--
-- Indexes for table `commerce_item_ship_info`
--
ALTER TABLE `commerce_item_ship_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ux_commerce_item_ship_info_ship_container_id` (`ship_container_id`);

--
-- Indexes for table `commerce_order`
--
ALTER TABLE `commerce_order`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `commerce_order_payment`
--
ALTER TABLE `commerce_order_payment`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ux_commerce_order_payment_billing_address_id` (`billing_address_id`),
  ADD UNIQUE KEY `ux_commerce_order_payment_card_id` (`card_id`),
  ADD UNIQUE KEY `ux_commerce_order_payment_epay_id` (`epay_id`),
  ADD KEY `fk_commerce_order_payment_commerce_order_id` (`commerce_order_id`);

--
-- Indexes for table `commerce_order_price`
--
ALTER TABLE `commerce_order_price`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_commerce_order_price_commerce_order_id` (`commerce_order_id`);

--
-- Indexes for table `commerce_order_price_ng`
--
ALTER TABLE `commerce_order_price_ng`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_commerce_order_price_ng_commerce_order_price_id` (`commerce_order_price_id`);

--
-- Indexes for table `commerce_payment_card`
--
ALTER TABLE `commerce_payment_card`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `commerce_shipping_container`
--
ALTER TABLE `commerce_shipping_container`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ux_commerce_shipping_container_price_id` (`price_id`),
  ADD KEY `fk_commerce_shipping_container_commerce_order_id` (`commerce_order_id`);

--
-- Indexes for table `commerce_ship_container_price`
--
ALTER TABLE `commerce_ship_container_price`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `commerce_ship_price_ng`
--
ALTER TABLE `commerce_ship_price_ng`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_commerce_ship_price_ng_commerce_ship_container_price_id` (`commerce_ship_container_price_id`);

--
-- Indexes for table `DATABASECHANGELOGLOCK`
--
ALTER TABLE `DATABASECHANGELOGLOCK`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `jhi_persistent_audit_event`
--
ALTER TABLE `jhi_persistent_audit_event`
  ADD PRIMARY KEY (`event_id`),
  ADD KEY `idx_persistent_audit_event` (`principal`,`event_date`);

--
-- Indexes for table `jhi_persistent_audit_evt_data`
--
ALTER TABLE `jhi_persistent_audit_evt_data`
  ADD PRIMARY KEY (`event_id`,`name`),
  ADD KEY `idx_persistent_audit_evt_data` (`event_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `commerce_billing_address`
--
ALTER TABLE `commerce_billing_address`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `commerce_e_pay`
--
ALTER TABLE `commerce_e_pay`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `commerce_item`
--
ALTER TABLE `commerce_item`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `commerce_item_pay_info`
--
ALTER TABLE `commerce_item_pay_info`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `commerce_item_price`
--
ALTER TABLE `commerce_item_price`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `commerce_item_price_ng`
--
ALTER TABLE `commerce_item_price_ng`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `commerce_item_ship_info`
--
ALTER TABLE `commerce_item_ship_info`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `commerce_order`
--
ALTER TABLE `commerce_order`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `commerce_order_payment`
--
ALTER TABLE `commerce_order_payment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `commerce_order_price`
--
ALTER TABLE `commerce_order_price`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `commerce_order_price_ng`
--
ALTER TABLE `commerce_order_price_ng`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `commerce_payment_card`
--
ALTER TABLE `commerce_payment_card`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `commerce_shipping_container`
--
ALTER TABLE `commerce_shipping_container`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `commerce_ship_container_price`
--
ALTER TABLE `commerce_ship_container_price`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `commerce_ship_price_ng`
--
ALTER TABLE `commerce_ship_price_ng`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `jhi_persistent_audit_event`
--
ALTER TABLE `jhi_persistent_audit_event`
  MODIFY `event_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `commerce_item`
--
ALTER TABLE `commerce_item`
  ADD CONSTRAINT `fk_commerce_item_commerce_order_id` FOREIGN KEY (`commerce_order_id`) REFERENCES `commerce_order` (`id`),
  ADD CONSTRAINT `fk_commerce_item_pay_info_id` FOREIGN KEY (`pay_info_id`) REFERENCES `commerce_item_pay_info` (`id`),
  ADD CONSTRAINT `fk_commerce_item_price_id` FOREIGN KEY (`price_id`) REFERENCES `commerce_item_price` (`id`),
  ADD CONSTRAINT `fk_commerce_item_ship_info_id` FOREIGN KEY (`ship_info_id`) REFERENCES `commerce_item_ship_info` (`id`);

--
-- Constraints for table `commerce_item_payment`
--
ALTER TABLE `commerce_item_payment`
  ADD CONSTRAINT `fk_commerce_item_payment_payments_id` FOREIGN KEY (`payments_id`) REFERENCES `commerce_order_payment` (`id`),
  ADD CONSTRAINT `fk_commerce_item_payment_commerce_items_id` FOREIGN KEY (`commerce_items_id`) REFERENCES `commerce_item` (`id`);

--
-- Constraints for table `commerce_item_pay_info`
--
ALTER TABLE `commerce_item_pay_info`
  ADD CONSTRAINT `fk_commerce_item_pay_info_order_payment_id` FOREIGN KEY (`order_payment_id`) REFERENCES `commerce_order_payment` (`id`);

--
-- Constraints for table `commerce_item_price_ng`
--
ALTER TABLE `commerce_item_price_ng`
  ADD CONSTRAINT `fk_commerce_item_price_ng_commerce_item_price_id` FOREIGN KEY (`commerce_item_price_id`) REFERENCES `commerce_item_price` (`id`);

--
-- Constraints for table `commerce_item_shipcontainer`
--
ALTER TABLE `commerce_item_shipcontainer`
  ADD CONSTRAINT `fk_commerce_item_shipcontainer_shipcontainers_id` FOREIGN KEY (`shipcontainers_id`) REFERENCES `commerce_shipping_container` (`id`),
  ADD CONSTRAINT `fk_commerce_item_shipcontainer_commerce_items_id` FOREIGN KEY (`commerce_items_id`) REFERENCES `commerce_item` (`id`);

--
-- Constraints for table `commerce_item_ship_info`
--
ALTER TABLE `commerce_item_ship_info`
  ADD CONSTRAINT `fk_commerce_item_ship_info_ship_container_id` FOREIGN KEY (`ship_container_id`) REFERENCES `commerce_shipping_container` (`id`);

--
-- Constraints for table `commerce_order_payment`
--
ALTER TABLE `commerce_order_payment`
  ADD CONSTRAINT `fk_commerce_order_payment_billing_address_id` FOREIGN KEY (`billing_address_id`) REFERENCES `commerce_billing_address` (`id`),
  ADD CONSTRAINT `fk_commerce_order_payment_card_id` FOREIGN KEY (`card_id`) REFERENCES `commerce_payment_card` (`id`),
  ADD CONSTRAINT `fk_commerce_order_payment_commerce_order_id` FOREIGN KEY (`commerce_order_id`) REFERENCES `commerce_order` (`id`),
  ADD CONSTRAINT `fk_commerce_order_payment_epay_id` FOREIGN KEY (`epay_id`) REFERENCES `commerce_e_pay` (`id`);

--
-- Constraints for table `commerce_order_price`
--
ALTER TABLE `commerce_order_price`
  ADD CONSTRAINT `fk_commerce_order_price_commerce_order_id` FOREIGN KEY (`commerce_order_id`) REFERENCES `commerce_order` (`id`);

--
-- Constraints for table `commerce_order_price_ng`
--
ALTER TABLE `commerce_order_price_ng`
  ADD CONSTRAINT `fk_commerce_order_price_ng_commerce_order_price_id` FOREIGN KEY (`commerce_order_price_id`) REFERENCES `commerce_order_price` (`id`);

--
-- Constraints for table `commerce_shipping_container`
--
ALTER TABLE `commerce_shipping_container`
  ADD CONSTRAINT `fk_commerce_shipping_container_commerce_order_id` FOREIGN KEY (`commerce_order_id`) REFERENCES `commerce_order` (`id`),
  ADD CONSTRAINT `fk_commerce_shipping_container_price_id` FOREIGN KEY (`price_id`) REFERENCES `commerce_ship_container_price` (`id`);

--
-- Constraints for table `commerce_ship_price_ng`
--
ALTER TABLE `commerce_ship_price_ng`
  ADD CONSTRAINT `fk_commerce_ship_price_ng_commerce_ship_container_price_id` FOREIGN KEY (`commerce_ship_container_price_id`) REFERENCES `commerce_ship_container_price` (`id`);

--
-- Constraints for table `jhi_persistent_audit_evt_data`
--
ALTER TABLE `jhi_persistent_audit_evt_data`
  ADD CONSTRAINT `fk_evt_pers_audit_evt_data` FOREIGN KEY (`event_id`) REFERENCES `jhi_persistent_audit_event` (`event_id`);
