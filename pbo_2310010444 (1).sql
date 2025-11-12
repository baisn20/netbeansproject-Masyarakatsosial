-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 12, 2025 at 04:48 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pbo_2310010444`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
('1', 'Dimas', 'dimas123'),
('2', 'kenny', '123'),
('7', 'Ronaldo', 'messi');

-- --------------------------------------------------------

--
-- Table structure for table `bansos`
--

CREATE TABLE `bansos` (
  `idbansos` varchar(50) NOT NULL,
  `uraian` varchar(50) NOT NULL,
  `jenisbantuan` varchar(50) NOT NULL,
  `tahun` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bansos`
--

INSERT INTO `bansos` (`idbansos`, `uraian`, `jenisbantuan`, `tahun`) VALUES
('1', 'Beras', 'Beras', '2023'),
('7', 'Pemberian ', 'Roti Gandum', '2025');

-- --------------------------------------------------------

--
-- Table structure for table `proker`
--

CREATE TABLE `proker` (
  `idproker` varchar(50) NOT NULL,
  `proker` varchar(50) NOT NULL,
  `waktupelaksana` varchar(50) NOT NULL,
  `penanggungjawab` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `proker`
--

INSERT INTO `proker` (`idproker`, `proker`, `waktupelaksana`, `penanggungjawab`) VALUES
('1', 'Pembangunan Jalan', 'Februari', 'Prabowo'),
('2', 'Penggundulan Hutan', '1 Bulan', 'Jokowi'),
('7', 'Pembangunan Stadion', '2027', 'Bais');

-- --------------------------------------------------------

--
-- Table structure for table `renbang`
--

CREATE TABLE `renbang` (
  `idrenbang` varchar(50) NOT NULL,
  `kegiatan` varchar(50) NOT NULL,
  `sasaran` varchar(50) NOT NULL,
  `keterangan` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `renbang`
--

INSERT INTO `renbang` (`idrenbang`, `kegiatan`, `sasaran`, `keterangan`) VALUES
('1', 'Gotong Royong', 'Kampung Lemo', 'Rencana'),
('7', 'Pembersihan Selokan', 'Warga', 'Sudah tercapai');

-- --------------------------------------------------------

--
-- Table structure for table `warga`
--

CREATE TABLE `warga` (
  `idwarga` varchar(50) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `jk` varchar(10) NOT NULL,
  `pekerjaan` varchar(50) NOT NULL,
  `alamat` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `warga`
--

INSERT INTO `warga` (`idwarga`, `nama`, `jk`, `pekerjaan`, `alamat`) VALUES
('1', 'Bais', 'Laki laki', 'Mahasiswa', 'HKSN'),
('6', 'Messi', 'Laki Laki', 'Professional Atlit', 'Inter Miami');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bansos`
--
ALTER TABLE `bansos`
  ADD PRIMARY KEY (`idbansos`);

--
-- Indexes for table `proker`
--
ALTER TABLE `proker`
  ADD PRIMARY KEY (`idproker`);

--
-- Indexes for table `renbang`
--
ALTER TABLE `renbang`
  ADD PRIMARY KEY (`idrenbang`);

--
-- Indexes for table `warga`
--
ALTER TABLE `warga`
  ADD PRIMARY KEY (`idwarga`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
