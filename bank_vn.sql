-- phpMyAdmin SQL Dump
-- version 4.8.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 15, 2018 at 06:48 PM
-- Server version: 10.1.33-MariaDB
-- PHP Version: 7.2.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bank_vn`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `taikhoan` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `matkhau` varchar(30) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`taikhoan`, `matkhau`) VALUES
('', '88888888'),
('root', 'root');

-- --------------------------------------------------------

--
-- Table structure for table `thechinh`
--

CREATE TABLE `thechinh` (
  `mathechinh` int(11) NOT NULL,
  `matkhau` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `hoten` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `sodu` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `thechinh`
--

INSERT INTO `thechinh` (`mathechinh`, `matkhau`, `hoten`, `sodu`) VALUES
(123456789, '1', 'Nguyen Van Tuan', 25000000),
(987654321, '1', 'Nguyen Thuy Trang', 30000000);

-- --------------------------------------------------------

--
-- Table structure for table `thephu`
--

CREATE TABLE `thephu` (
  `mathephu` int(11) NOT NULL,
  `mathechinh` int(11) NOT NULL,
  `matkhau` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `hotenthephu` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `hanmuc` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `thephu`
--

INSERT INTO `thephu` (`mathephu`, `mathechinh`, `matkhau`, `hotenthephu`, `hanmuc`) VALUES
(666666666, 987654321, '1', 'Ngo Gia Vu', 7000000),
(777777777, 987654321, '1', 'Ngo Ba Thi', 6000000),
(888888888, 123456789, '1', 'Nguyen Quynh Anh', 8000000),
(999999999, 123456789, '1', 'Nguyen Tuan Dat', 7000000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `thechinh`
--
ALTER TABLE `thechinh`
  ADD PRIMARY KEY (`mathechinh`);

--
-- Indexes for table `thephu`
--
ALTER TABLE `thephu`
  ADD PRIMARY KEY (`mathephu`),
  ADD KEY `mathechinh` (`mathechinh`),
  ADD KEY `mathechinh_2` (`mathechinh`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `thephu`
--
ALTER TABLE `thephu`
  ADD CONSTRAINT `thephu_ibfk_1` FOREIGN KEY (`mathechinh`) REFERENCES `thechinh` (`mathechinh`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
