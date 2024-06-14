import numpy as np
import pandas as pd
import tensorflow as tf
import tensorflow_io as tfio
import librosa
import soundfile as sf


# def cast_to_16bit(file):
#     try:
#         data, sample_rate = sf.read(file)
#         max16 = np.iinfo(np.int16).max
#         scaled_data = np.int16(data * max16)
#         return scaled_data, sample_rate
#     except Exception as e:
#         raise f"Error reading or converting to 16-bit: {e}"


# def load_wav(file_content):
#     try:
#         wav, sample_rate = tf.audio.decode_wav(file_content, desired_channels=1)
#         wav = tf.squeeze(wav, axis=1)
#         sample_rate = tf.cast(sample_rate, tf.int32)
#         return tfio.audio.resample(wav, rate_in=sample_rate, rate_out=32000)
    
#     except Exception as e:
#         print(f"Error decoding audio, attempting 16-bit conversion: {e}")
#         # Attempt to convert to 16-bit using cast_to_16bit function
#         scaled_data, sample_rate = cast_to_16bit(file_content)
        
#         if scaled_data is not None:
#             # Create TensorFlow tensor from numpy array
#             wav_tensor = tf.convert_to_tensor(scaled_data, dtype=tf.int16)
#             sample_rate = tf.cast(sample_rate, tf.int32)
#             return tfio.audio.resample(wav_tensor, rate_in=sample_rate, rate_out=32000)
#         else:
#             return None

# def load_wav(file_content):
#     data, sample_rate = sf.read(file_content)
#     max16 = np.iinfo(np.int16).max
#     scaled_data = np.int16(data * max16)
#     wav = tf.squeeze(scaled_data, axis=1)
#     sample_rate = tf.cast(sample_rate, tf.int32)
#     return tfio.audio.resample(wav, rate_in=sample_rate, rate_out=32_000)


def load_wav(file_content):
    wav, sample_rate = tf.audio.decode_wav(file_content, desired_channels=1)
    wav = tf.squeeze(wav, axis=-1)
    sample_rate = tf.cast(sample_rate, dtype=tf.int64)
    return tfio.audio.resample(wav, rate_in=sample_rate, rate_out=32_000)


def add_noise(wav, noise_factor=0.005):
    noise = tf.random.normal(shape=tf.shape(wav), mean=0.0, stddev=noise_factor, dtype=tf.float32)
    return (wav + noise).numpy()


def preprocess(file_content):
    wav = load_wav(file_content)

    wav = wav[:96_000]
    zero_padding = tf.zeros([96_000] - tf.shape(wav), dtype=tf.float32)
    wav = tf.concat([zero_padding, wav], 0)

    spectrogram = tf.signal.stft(wav, frame_length=320, frame_step=32)
    spectrogram = tf.abs(spectrogram)
    spectrogram = tf.expand_dims(spectrogram, axis=-1) 
    spectrogram = tf.concat([spectrogram] * 3, axis=-1) 
    return spectrogram.numpy()


def parse_inceptionv3(data_input, model):
    preprocessed_input = tf.expand_dims(data_input, axis=0)
    features = model(preprocessed_input)
    flattened_output = tf.keras.layers.Flatten()(features)
    return flattened_output.numpy()


def load_data(file, model):
    # file_content = tf.io.read_file(file)
    file_content = file.read()
    # file_type = 'wav' if file_content[:4] == b'RIFF' else 'mp3'
    spectrogram = preprocess(file_content)
    spectrogram = add_noise(spectrogram)
    spectrogram = parse_inceptionv3(spectrogram, model)
    return spectrogram
