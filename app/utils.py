import numpy as np
import pandas as pd
import tensorflow as tf
import tensorflow_io as tfio
import librosa


def load_audio(file_content, target_sr):
    audio, sample_rate = (
        librosa.load(file_content, sr=target_sr, mono=True))
    wav_resampled = librosa.resample(audio, orig_sr=sample_rate, target_sr=32000)
    return wav_resampled, target_sr


def preprocess(file_content, target_sr=32000, n_fft=320, hop_length=32, n_mels=128, fmin=80.0, fmax=7600.0):
    audio, sample_rate = load_audio(file_content, target_sr)
    target_samples = int(np.ceil(len(audio) / sample_rate) * target_sr)
    if len(audio) < target_samples:
        audio = np.pad(audio, (0, target_samples - len(audio)), mode='constant')
    mel_spectrogram = librosa.feature.melspectrogram(
        y=audio, 
        sr=target_sr, 
        n_mels=n_mels,
        fmin=fmin,
        fmax=fmax
    )
    mel_spectrogram_db = librosa.power_to_db(mel_spectrogram, ref=np.min)
    spectogram = np.expand_dims(mel_spectrogram_db, axis=-1)
    spectogram = np.repeat(spectogram, 3, axis=-1)
    return spectogram


def add_noise(wav, noise_factor=0.005):
    noise = tf.random.normal(shape=tf.shape(wav), mean=0.0, stddev=noise_factor, dtype=tf.float32)
    return (wav + noise).numpy()


def parse_inceptionv3(data_input, model):
    preprocessed_input = tf.expand_dims(data_input, axis=0)
    features = model(preprocessed_input)
    flattened_output = tf.keras.layers.Flatten()(features)
    return flattened_output.numpy()


def load_data(file, model):
    # file_content = tf.io.read_file(file)
    # file_content = file.read()
    # print("File content:", file_content[:100])
    # file_type = 'wav' if file_content[:4] == b'RIFF' else 'mp3'
    spectrogram = preprocess(file)
    spectrogram = add_noise(spectrogram)
    spectrogram = parse_inceptionv3(spectrogram, model)
    return spectrogram
