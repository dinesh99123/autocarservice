-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 20, 2021 at 08:05 AM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `autocarservice`
--

-- --------------------------------------------------------

--
-- Table structure for table `addreg`
--

DROP TABLE IF EXISTS `addreg`;
CREATE TABLE IF NOT EXISTS `addreg` (
  `AdminID` int(20) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(50) NOT NULL,
  `Password` varchar(40) NOT NULL,
  `NIC` varchar(12) NOT NULL,
  `ContactNumber` int(20) NOT NULL,
  PRIMARY KEY (`AdminID`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `addreg`
--

INSERT INTO `addreg` (`AdminID`, `UserName`, `Password`, `NIC`, `ContactNumber`) VALUES
(8, 'Admin', 'Admin123', '1996232322', 774695236),
(11, 'saandeepa', '2001200', '2000122', 7154623);

-- --------------------------------------------------------

--
-- Table structure for table `customertbl`
--

DROP TABLE IF EXISTS `customertbl`;
CREATE TABLE IF NOT EXISTS `customertbl` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(70) NOT NULL,
  `Address` varchar(75) NOT NULL,
  `Contactnumber` varchar(10) NOT NULL,
  `Email` varchar(25) NOT NULL,
  `VehicalType` varchar(50) NOT NULL,
  `Vehicalnumber` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customertbl`
--

INSERT INTO `customertbl` (`ID`, `Name`, `Address`, `Contactnumber`, `Email`, `VehicalType`, `Vehicalnumber`) VALUES
(5, 'Dinesh', 'hambantota', '0775235682', 'din@gmail.com', 'honda', '100');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `EmployeeID` int(30) NOT NULL AUTO_INCREMENT,
  `FullName` varchar(100) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `NIC` varchar(12) NOT NULL,
  `ContactNumber` int(10) NOT NULL,
  PRIMARY KEY (`EmployeeID`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`EmployeeID`, `FullName`, `Email`, `NIC`, `ContactNumber`) VALUES
(1, 'sandeepa kavinda', 'sandee11@gmail.com', '200016533262', 776525368),
(2, 'avishka', 'saaa@gmail.com', '20001355', 12545);

-- --------------------------------------------------------

--
-- Table structure for table `empsalary`
--

DROP TABLE IF EXISTS `empsalary`;
CREATE TABLE IF NOT EXISTS `empsalary` (
  `EmployeeName` varchar(50) NOT NULL,
  `BasicSalary` int(100) NOT NULL,
  `Bonus` int(100) NOT NULL,
  `OTHours` int(24) NOT NULL,
  `OTRate` int(100) NOT NULL,
  `OTAmount` int(100) NOT NULL,
  `TotalSalary` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `empsalary`
--

INSERT INTO `empsalary` (`EmployeeName`, `BasicSalary`, `Bonus`, `OTHours`, `OTRate`, `OTAmount`, `TotalSalary`) VALUES
('sssss', 1000, 20, 6, 100, 600, 1620),
('kiri saman', 1000, 20, 6, 1000, 6000, 7020),
('avishka', 30000, 1500, 8, 100, 800, 32300);

-- --------------------------------------------------------

--
-- Table structure for table `mre`
--

DROP TABLE IF EXISTS `mre`;
CREATE TABLE IF NOT EXISTS `mre` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(300) NOT NULL,
  `date` varchar(50) NOT NULL,
  `day income` float NOT NULL,
  `expenses amount` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mre`
--

INSERT INTO `mre` (`id`, `description`, `date`, `day income`, `expenses amount`) VALUES
(12, 'saaaa', '2021', 10, 10),
(2, 'ss', '2021', 10, 10),
(3, 'wages', '2020', 10000, 1000),
(4, 'wages', '2019', 1500, 100);

-- --------------------------------------------------------

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
CREATE TABLE IF NOT EXISTS `purchase` (
  `PurchaseID` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `SupplierName` varchar(250) NOT NULL,
  `TotalCost` int(11) NOT NULL,
  `Payment` int(11) NOT NULL,
  `Balance` int(11) NOT NULL,
  PRIMARY KEY (`PurchaseID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `purchaseitem`
--

DROP TABLE IF EXISTS `purchaseitem`;
CREATE TABLE IF NOT EXISTS `purchaseitem` (
  `PurchaseItemID` int(11) NOT NULL AUTO_INCREMENT,
  `PurchaseID` int(11) NOT NULL,
  `Barcode` int(11) NOT NULL,
  `Cost_Price` int(11) NOT NULL,
  `Qty` int(11) NOT NULL,
  `TotalCost` int(11) NOT NULL,
  PRIMARY KEY (`PurchaseItemID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `regis`
--

DROP TABLE IF EXISTS `regis`;
CREATE TABLE IF NOT EXISTS `regis` (
  `Password` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `regis`
--

INSERT INTO `regis` (`Password`) VALUES
('1567'),
('2560'),
('sss'),
('1520'),
('1234');

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
CREATE TABLE IF NOT EXISTS `sales` (
  `SalesID` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `TotalCost` int(11) NOT NULL,
  `Payment` int(11) NOT NULL,
  `Balance` int(11) NOT NULL,
  PRIMARY KEY (`SalesID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `salesitem`
--

DROP TABLE IF EXISTS `salesitem`;
CREATE TABLE IF NOT EXISTS `salesitem` (
  `SalesItemID` int(11) NOT NULL AUTO_INCREMENT,
  `SalesID` int(11) NOT NULL,
  `Barcode` int(11) NOT NULL,
  `Rental_Price` int(11) NOT NULL,
  `Qty` int(11) NOT NULL,
  `TotalCost` int(11) NOT NULL,
  PRIMARY KEY (`SalesItemID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
CREATE TABLE IF NOT EXISTS `service` (
  `serviceid` int(11) NOT NULL AUTO_INCREMENT,
  `servicename` varchar(200) NOT NULL,
  `servicecharge` varchar(255) NOT NULL,
  `employeecharge` varchar(100) NOT NULL,
  PRIMARY KEY (`serviceid`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`serviceid`, `servicename`, `servicecharge`, `employeecharge`) VALUES
(1, 'sam', '10.0', '100.0');

-- --------------------------------------------------------

--
-- Table structure for table `spareparts`
--

DROP TABLE IF EXISTS `spareparts`;
CREATE TABLE IF NOT EXISTS `spareparts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Sparse_int` varchar(150) NOT NULL,
  `Description` varchar(250) NOT NULL,
  `Barcode` int(11) NOT NULL,
  `Qty` int(11) NOT NULL,
  `Cost_Price` int(11) NOT NULL,
  `Rental_Price` int(11) NOT NULL,
  `Recorder_Level` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `SupplierName` varchar(250) NOT NULL,
  `Phone` varchar(10) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Address` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id`, `SupplierName`, `Phone`, `Email`, `Address`) VALUES
(1, 'Thula', '0774625368', 'sand@gmail.com', 'statatagabga');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
